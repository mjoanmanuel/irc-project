package com.irc.gui.chat.panel;

import static com.irc.gui.factory.ComponentFactory.createButton;
import static com.irc.gui.factory.ComponentFactory.createLabel;
import static com.irc.server.Connector.DEFAULT_IRC_PORT;
import static com.irc.server.utils.ProtocolUtils.SPACE;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.showInputDialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.irc.gui.utils.I18nUtils;

/**
 * ChatPanel is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public class ChatPanel extends JPanel {

    private static final String BLANK_INPUT_TEXT_AREA = "";

    /**
     * MainPanel is responsible of
     * 
     * @author mjoanmanuel@gmail.com
     */
    private class MainPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public MainPanel() {
	    setLayout(new BorderLayout());
	    add(createLabel(properties.getString("nickname")
		    + client.getNickName()), NORTH);
	    add(createRecieveMessageTextArea(), CENTER);
	}

	private JScrollPane createRecieveMessageTextArea() {
	    recieveMessage = new JTextArea();
	    recieveMessage.setRows(10);
	    recieveMessage.setColumns(40);
	    recieveMessage.setWrapStyleWord(true);
	    recieveMessage.setEditable(false);
	    final JScrollPane scrollPane = new JScrollPane(recieveMessage);
	    return scrollPane;
	}

    }

    /**
     * TextPanel is responsible of
     * 
     * @author mjoanmanuel@gmail.com
     */
    private class TextPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public TextPanel() {
	    setLayout(new BorderLayout());
	    add(createLabel(properties.getString("messageLabel")), NORTH);
	    add(createInputMessageTextArea(), CENTER);
	    add(createButton(properties.getString("sendMessage"),
		    sendMessageAction()), EAST);
	}

	private AbstractAction sendMessageAction() {
	    return new AbstractAction() {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(final ActionEvent e) {
		    client.sendMessage(inputMessage.getText());
		    inputMessage.setText(BLANK_INPUT_TEXT_AREA);
		}
	    };
	}

	private JScrollPane createInputMessageTextArea() {
	    inputMessage = new JTextArea();
	    inputMessage.setRows(3);
	    inputMessage.setColumns(40);
	    inputMessage.setWrapStyleWord(true);
	    final JScrollPane scrollPane = new JScrollPane(inputMessage);
	    return scrollPane;
	}

    }

    private class ClientPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private DefaultListModel listModel = new DefaultListModel();

	public ClientPanel() {
	    setLayout(new BorderLayout());
	    add(createLabel(properties.getString("online")), NORTH);
	    add(createConnectedClientList(), CENTER);
	}

	private JScrollPane createConnectedClientList() {
	    final JList list = new JList(listModel);
	    return new JScrollPane(list);
	}

	public void updateList(final String nickname, final boolean update) {
	    if (update) {
		if (!listModel.contains(nickname)) {
		    listModel.addElement(nickname);
		}
	    } else {
		listModel.removeElement(nickname);
	    }
	}
    }

    private class ClientImpl {

	private static final boolean REMOVE = false;
	private static final boolean UPDATE = true;
	private static final String LEFT_MSG = "dejado";
	private static final boolean RUN_WILDLY = true;
	private static final boolean AUTO_FLUSH = true;

	private String nickname;
	private String channelname;

	private PrintWriter sender;
	private BufferedReader reader;

	public ClientImpl(final String nickname, final String channelname) {
	    this.nickname = nickname;
	    this.channelname = channelname;
	}

	public synchronized void start() {
	    Socket client = null;

	    try {
		client = new Socket(InetAddress.getLocalHost(),
			DEFAULT_IRC_PORT);
		sender = new PrintWriter(client.getOutputStream(), AUTO_FLUSH);

		sendCfg();

		reader = new BufferedReader(new InputStreamReader(
			client.getInputStream()));

		readerThread();

		while (RUN_WILDLY) {
		}

	    } catch (final IOException e) {
		e.printStackTrace();
	    }
	}

	public void sendMessage(final String message) {
	    sender.println(message);
	}

	public String getNickName() {
	    return nickname;
	}

	private void sendCfg() {
	    sender.println(nickname + "," + channelname);
	}

	private void readerThread() {
	    new Thread() {
		@Override
		public synchronized void run() {
		    super.run();
		    try {
			String read = BLANK_INPUT_TEXT_AREA;
			while ((read = reader.readLine()) != null) {
			    if (read.contains("[onlinelist]")) {
				updateList(null, UPDATE);
			    } else {
				if (!hasLeftMsg(read)) {
				    recieveMessage.append(read + "\n");
				} else {
				    final String nickname = read.split(SPACE)[1];
				    updateList(nickname, REMOVE);
				    recieveMessage.append(read + "\n");
				}
			    }
			}
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		}

		private boolean hasLeftMsg(final String read) {
		    final String[] split = read.split(SPACE);
		    for (final String elem : split) {
			if (elem.equals(LEFT_MSG))
			    return true;
		    }
		    return false;
		}

		private void updateList(final String nickname,
			final boolean update) throws IOException {
		    String read;
		    if (update) {
			sender.println("[get]");
			while (!(read = reader.readLine()).equals("[end]")) {
			    clientPanel.updateList(read, update);
			}
		    } else {
			clientPanel.updateList(nickname, update);
		    }
		}
	    }.start();
	}

    }

    private static final String FILE_I18N = ChatPanel.class.getCanonicalName();
    private static final I18nUtils properties = new I18nUtils(FILE_I18N);

    private static final long serialVersionUID = 1L;

    private ClientImpl client;
    private ClientPanel clientPanel = new ClientPanel();
    private JTextArea recieveMessage;
    private JTextArea inputMessage;

    public ChatPanel() {
	configure();
	setLayout(new BorderLayout());
	final MainPanel mainPanel = new MainPanel();
	mainPanel.add(new TextPanel(), SOUTH);
	add(mainPanel);
	setVisible(true);
    }

    public JPanel getClientPanel() {
	return clientPanel;
    }

    private void configure() {
	final String nickname = showInputDialog(null, "usuario: ", "Usuario",
		QUESTION_MESSAGE);
	final String channelname = showInputDialog(null, "canal: ",
		"Canal -> *Debe tener # o & al inicio.", QUESTION_MESSAGE);
	client = new ClientImpl(nickname, channelname);
	new Thread() {
	    @Override
	    public void run() {
		super.run();
		client.start();
	    }
	}.start();
    }

}
