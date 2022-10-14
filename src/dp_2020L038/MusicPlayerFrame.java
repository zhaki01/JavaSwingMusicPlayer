package dp_2020L038;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import jaco.mp3.player.MP3Player;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JProgressBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 * 
 * @author zhakiDapeng
 * @version 1.0 第一个人音乐播放器界面 数据库为test_music. 数据表为music_test
 *          https://music.163.com/
 */
public class MusicPlayerFrame {

	public JFrame frame; // 窗口
	private JPanel mainPanel; // 主面板
	private JPanel panelTableMain; // 在JTable上的主面板

	private JPanel subPanelTable; // 在JTable 上的子面板
	private JScrollPane scrollPaneOfTable; // 滚动面板
	private JTable table; // 表格

	/*
	 * JTable 上面的标签以及文本框, 为了用户可以把个人音乐上传到数据库
	 */
	private JLabel lblid_music; // 编号
	private JTextField textMusicID;
	private JLabel lblname_music; // 歌名
	private JLabel lblfilePath_music; // 文件路径
	private JTextField textMusicNamePath;

	private JPanel panelControlButtons; // 在上面有所有音乐控制按钮
	private JProgressBar progressBar; // 进度条
	private JButton btnUploadMusic; // 从本地上传到并播放音乐等操作

	/**
	 * 音乐管理按钮
	 */
	private JButton btnPreviouse; // 上一首一个的按钮
	private JButton btnPause; // 暂停
	private JButton btnPlay; // 播放
	private JButton btnNext; // 下一首个
	private JButton btnStop; // 停止按钮
	private JButton btnOpenFile; // 打开文件夹选择音乐存储到数据上

	/*
	 * 音量管理的那些按钮
	 */
	private JButton btnVolumeDown;
	private JButton btnVolumeUp;
	private JButton btnVolumeFull;
	private JButton btnMute;

	private JButton btnChangeToOldIntr; // 切换到另外一个页面, 从本地多选歌曲上传到JList列表上

	// 从本地来上传的音乐名字可以显示到lblMusicNameDysplay标签上
	private JPanel panelMainMusicDisplayName;
	private JPanel subPanelMusicDispName;
	private JLabel lblMusicNameDysplay;

	/*
	 * 设置动态的GIF图片的位置
	 */
	private JPanel panelAnimation; // 主面板
	private JPanel subAnimationPanel;
	private JLabel lblAnimation;

	/*
	 * 加一个功能, 用户可以通过输入任何一个网址URL访问到那个网站上
	 */
	private JLabel lblURL;
	private JTextField textURL;
	private JButton btnOpenUrl;
	private JButton btnCanselUrl;

	/*
	 * 设置Logo
	 */
	private JPanel panelHeader;
	private JLabel lblTitle; // 标题

	private JButton btnReadMusicInfo; // 这个按钮已经存储到数据库上的歌曲以及歌曲路径读出来
	private JButton btnUploadMusicToDatabase; // 这个按钮从本地来的音乐以及他的路径存储到数据库上
	private JButton btnToDatabases; // 去增删改查界面
	private JButton btnToLogin; // 返回到登录界面的按钮

	/*
	 * 打开文件
	 */

	private JLabel lblSongNameDysplay;

	String strPath = "", strPathNew;

	/*
	 * 编写代码开始之前, 必须添加mp3播放器库
	 */
	/*
	 * 定义一些通用的变量 JACO MP3Player
	 */
	MP3Player player;

	/*
	 * 文件以使播放器文件强大, 可以存储歌曲的位置的文件
	 */
	File songFile;

	/*
	 * 字符串作为当前的目录 这将是默认的
	 * 
	 */
	String currentDirectory = "home/user";

	boolean repeat = false; // 一检查是否启用了播放器重复或不是

	long pauseLoc, songLength;
	int playstatus = 0, filepathresponse, trackNo = 0;

	FileInputStream fis1;

	BufferedInputStream bis1;

//===============================
	File f = null;
	String path = null;

	// 数据库
	private static final String username = "root";
	private static final String password = "dapeng";
	private static final String dataConn = "jdbc:mysql://localhost/test_music";
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	protected static final String SelectedRows = null;
	// 连接
	Connection sqlConn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	int q, i, num, deleteItem;
	int numUpdate;
	protected int update;

	private DefaultListModel<String> listModel;
	JFileChooser fcPath = new JFileChooser();
	File[] selectedFile;

//	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MusicPlayerFrame window = new MusicPlayerFrame();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

//	String path;

	private JTextField textNameMusic;

	private JButton btnRest;

	// ======================刷新数据库============================
	public void upDate() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // 启动我的驱动程序]
			sqlConn = DriverManager.getConnection(dataConn, username, password);
			pst = sqlConn.prepareStatement("select * from music_test");
			// 结果
			rs = pst.executeQuery();
			ResultSetMetaData stData = rs.getMetaData();
			q = stData.getColumnCount(); // lie

			DefaultTableModel RecordTable = (DefaultTableModel) table.getModel();
			RecordTable.setRowCount(0);

			while (rs.next()) {
				Vector<String> columnData = new Vector<String>();

				for (i = 1; i <= q; i++) {
					columnData.add(rs.getString("id"));
					columnData.add(rs.getString("musicname"));
					columnData.add(rs.getString("musicpath"));
				}
				RecordTable.addRow(columnData);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}

	// ========================刷新结束==========================

	/**
	 * 构造函数
	 */
	public MusicPlayerFrame() {
		initialize();

		// 设置一个默认的歌曲文件
		songFile = new File("/Users/zhaki/Desktop/Music");
		

		// 设置一个字符串来获取文件名。
		String fileName = songFile.getName();

		// 在这里用这个名字设置我们的歌曲名称标签

		// 将方法添加到播放器变量中
		player = mp3Player();

		// 将歌曲作为播放列表添加到播放器
		player.addToPlayList(songFile);

	}

	/**
	 * 初始化框架的内容
	 */
	private void initialize() {
		/*
		 * 设置窗体
		 */
		frame = new JFrame();
		frame.setPreferredSize(new Dimension(250, 0));
		frame.setBounds(100, 100, 654, 662);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("第一个个人音乐管理系统");
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null); // 中心
		frame.setUndecorated(false);
		frame.setResizable(false);// 大小不能该

		/**
		 * 设置主面板
		 */
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBackground(new Color(20, 40, 80));
		mainPanel.setBounds(0, 0, 654, 634);
		frame.getContentPane().add(mainPanel);

		/**
		 * 设置JTable列表的面板
		 */
		panelTableMain = new JPanel();
		panelTableMain.setLayout(null);
		panelTableMain.setBackground(new Color(20, 40, 80));
		panelTableMain.setBounds(10, 62, 312, 392);
		mainPanel.add(panelTableMain);
		// 他的子面板
		subPanelTable = new JPanel();
		subPanelTable.setBounds(6, 6, 300, 380);
		panelTableMain.add(subPanelTable);
		subPanelTable.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		subPanelTable.setBackground(new Color(20, 40, 80));

		/**
		 * 在JTable的子面板上设置边框
		 */
		Border blackline = BorderFactory.createLineBorder(Color.black);
		subPanelTable.setLayout(null);

		/**
		 * 创建滚动面板
		 */
		scrollPaneOfTable = new JScrollPane();
		scrollPaneOfTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneOfTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneOfTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel RecordTable = (DefaultTableModel) table.getModel();
				int SelectedRows = table.getSelectedRow();
				textMusicID.setText(RecordTable.getValueAt(SelectedRows, 1).toString());
				textNameMusic.setText(RecordTable.getValueAt(SelectedRows, 1).toString());
				textMusicNamePath.setText(RecordTable.getValueAt(SelectedRows, 1).toString());
			}
		});
		scrollPaneOfTable.setBounds(6, 106, 288, 268);
		subPanelTable.add(scrollPaneOfTable);

		/*
		 * JTable表格
		 */
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable table = (JTable) e.getSource();
					int row = table.rowAtPoint(e.getPoint());
					int viewColumn = convertColumnIndexToView(1);

					String name = (String) table.getValueAt(row, viewColumn);
					player.setName(name);
				}
			}

			private int convertColumnIndexToView(int i) {
				// TODO Auto-generated method stub
				return 0;
			}
		});
		table.setForeground(Color.BLACK);
		table.setFont(new Font("Raanana", Font.BOLD, 12));
		table.setBackground(Color.WHITE);
		table.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "编号", "歌名", "文件路径" }));

		scrollPaneOfTable.setViewportView(table);

		/**
		 * JTable上面的标签 编号, 歌名, 文件路径以及取消按钮, 文本框
		 */
		lblid_music = new JLabel("编号");
		lblid_music.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblid_music.setForeground(new Color(0, 191, 255));
		lblid_music.setBounds(6, 6, 99, 16);
		subPanelTable.add(lblid_music);

		textMusicID = new JTextField();
		textMusicID.setBounds(117, 1, 177, 26);
		subPanelTable.add(textMusicID);
		textMusicID.setColumns(10);

		lblname_music = new JLabel("歌名");
		lblname_music.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblname_music.setForeground(new Color(0, 191, 255));
		lblname_music.setBounds(6, 31, 99, 16);
		subPanelTable.add(lblname_music);

		textNameMusic = new JTextField();
		textNameMusic.setColumns(10);
		textNameMusic.setBounds(117, 26, 177, 26);
		subPanelTable.add(textNameMusic);

		lblfilePath_music = new JLabel("文件路径");
		lblfilePath_music.setForeground(new Color(0, 191, 255));
		lblfilePath_music.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblfilePath_music.setBounds(6, 60, 99, 16);
		subPanelTable.add(lblfilePath_music);

		textMusicNamePath = new JTextField();
		textMusicNamePath.setColumns(10);
		textMusicNamePath.setBounds(117, 55, 177, 26);
		subPanelTable.add(textMusicNamePath);

		// 重置
		btnRest = new JButton("取消");
		btnRest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textMusicID.setText("");
				textNameMusic.setText("");
				textMusicNamePath.setText("");
			}
		});
		btnRest.setForeground(new Color(0, 191, 255));
		btnRest.setBounds(195, 79, 99, 29);
		subPanelTable.add(btnRest);

		/**
		 * panelControlButtons面板上边有进度条, 从本地上传的按钮, 播放, 暂停等按钮
		 */
		panelControlButtons = new JPanel();
		panelControlButtons.setLayout(null);
		panelControlButtons.setBackground(new Color(20, 40, 80));
		panelControlButtons.setBounds(10, 522, 634, 95);
		mainPanel.add(panelControlButtons);

		progressBar = new JProgressBar();
		progressBar.setForeground(new Color(0, 191, 255));
		progressBar.setFont(new Font("Libian SC", Font.BOLD, 13));
		progressBar.setBounds(41, 13, 547, 20);
		progressBar.setValue(2);
//		progressBar.setStringPainted(true);
		panelControlButtons.add(progressBar);
		progressBar.setBackground(new Color(0, 0, 0));

		/**
		 * panelMainMusicDisplayName面板上有子面板subPanelMusicDispName它们俩的 上面有JLabel
		 * lblMusicNameDysplay 标签显示正在播放的音乐
		 */
		panelMainMusicDisplayName = new JPanel();
		panelMainMusicDisplayName.setBounds(10, 466, 634, 44);
		mainPanel.add(panelMainMusicDisplayName);
		panelMainMusicDisplayName.setLayout(null);

		subPanelMusicDispName = new JPanel();
		subPanelMusicDispName.setBackground(new Color(0, 0, 0));
		subPanelMusicDispName.setBounds(6, 6, 622, 32);
		panelMainMusicDisplayName.add(subPanelMusicDispName);
		subPanelMusicDispName.setLayout(null);

		lblMusicNameDysplay = new JLabel("   要听音乐🎵吗?");
		lblMusicNameDysplay.setIconTextGap(1);
//		lblMusicNameDysplay.setIcon(new ImageIcon("AudioIcons/gifgit.gif"));
		lblMusicNameDysplay.setIcon(new ImageIcon(getClass().getClassLoader().getResource("gifgit.gif")));
		lblMusicNameDysplay.setForeground(new Color(135, 206, 250));
		lblMusicNameDysplay.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblMusicNameDysplay.setHorizontalAlignment(SwingConstants.CENTER);
		lblMusicNameDysplay.setBounds(6, 6, 610, 20);
		subPanelMusicDispName.add(lblMusicNameDysplay);

		panelAnimation = new JPanel();
		panelAnimation.setLayout(null);
		panelAnimation.setBackground(new Color(20, 40, 80));
		panelAnimation.setBounds(334, 62, 312, 392);
		mainPanel.add(panelAnimation);

		/**
		 * 有两个GIF格式的动态图片subAnimationPanel
		 */
		subAnimationPanel = new JPanel();
		subAnimationPanel.setLayout(null);
		subAnimationPanel.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		subAnimationPanel.setBackground(new Color(0, 0, 0));
		subAnimationPanel.setBounds(6, 130, 300, 256);
		panelAnimation.add(subAnimationPanel);

		/*
		 * 用户可以使用鼠标切换各种动态的图片
		 */
		lblAnimation = new JLabel("");
		lblAnimation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				String click;
//				lblAnimation.setIcon(new ImageIcon("AudioIcons/mybestdancer.gif"));
				lblAnimation.setIcon(new ImageIcon(getClass().getClassLoader().getResource("mybestdancer.gif")));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("你释放了鼠标 - ");
//				lblAnimation.setIcon(new ImageIcon("AudioIcons/gifMusic2.gif"));
				lblAnimation.setIcon(new ImageIcon(getClass().getClassLoader().getResource("gifMusic2.gif")));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("你按了鼠标");
//				lblAnimation.setIcon(new ImageIcon("AudioIcons/gifMusic2.gif"));
				lblAnimation.setIcon(new ImageIcon(getClass().getClassLoader().getResource("gifMusic2.gif")));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				System.out.println("你把这个部件放进去了-");
//				lblAnimation.setIcon(new ImageIcon("AudioIcons/musicGif.gif"));
				lblAnimation.setIcon(new ImageIcon(getClass().getClassLoader().getResource("musicGif.gif")));
			}
		});
		lblAnimation.setHorizontalAlignment(SwingConstants.CENTER);
//		lblAnimation.setIcon(new ImageIcon("AudioIcons/animated5.gif"));
		lblAnimation.setIcon(new ImageIcon(getClass().getClassLoader().getResource("animated5.gif")));
		lblAnimation.setBounds(6, 6, 288, 244);
		subAnimationPanel.add(lblAnimation);

		/*
		 * 用户可以输入自己想要访问的网站去浏览
		 */
		lblURL = new JLabel("请输入您想要访问到的网址URL");
		lblURL.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblURL.setForeground(new Color(0, 191, 255));
		lblURL.setHorizontalAlignment(SwingConstants.CENTER);
		lblURL.setBounds(6, 6, 300, 25);
		panelAnimation.add(lblURL);

		textURL = new JTextField();
		textURL.setBounds(6, 43, 300, 33);
		panelAnimation.add(textURL);
		textURL.setColumns(10);

		/*
		 * 打开网站的按钮
		 */
		btnOpenUrl = new JButton("访问网址");
		btnOpenUrl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				urlOpener(textURL.getText());
			}
		});
		btnOpenUrl.setForeground(new Color(0, 191, 255));
		btnOpenUrl.setBounds(189, 88, 117, 29);
		panelAnimation.add(btnOpenUrl);

		// 取消访问的按钮
		btnCanselUrl = new JButton("取消访问");
		btnCanselUrl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textURL.setText("");
			}
		});
		btnCanselUrl.setForeground(new Color(0, 191, 255));
		btnCanselUrl.setBounds(6, 89, 117, 29);
		panelAnimation.add(btnCanselUrl);

		/**
		 * panelHeader面板上有最上面的Logo刷新, 把音乐上传到数据库, 返回登录页面的按钮
		 */
		panelHeader = new JPanel();
		panelHeader.setLayout(null);
		panelHeader.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		panelHeader.setBackground(new Color(0, 0, 0));
		panelHeader.setBounds(0, 0, 654, 50);
		mainPanel.add(panelHeader);

		lblTitle = new JLabel("    MP3播放器");
		lblTitle.setFont(new Font("Raanana", Font.BOLD, 19));
		lblTitle.setForeground(new Color(135, 206, 250));
//		lblTitle.setIcon(new ImageIcon("AudioIcons/appGif.gif"));
		lblTitle.setIcon(new ImageIcon(getClass().getClassLoader().getResource("appGif.gif")));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(6, 6, 383, 38);
		panelHeader.add(lblTitle);

		/**
		 * 返回登录页面的按钮
		 */
		btnToLogin = new JButton("");
		btnToLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dispose();
				Login login = new Login();
				login.frame.setLocationRelativeTo(null);
				login.frame.setVisible(true);

			}
		});

		// 把数据刷新以及显示到列表上按钮
		btnReadMusicInfo = new JButton("");
		btnReadMusicInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel RecordTable = (DefaultTableModel) table.getModel();
				int SelectedRows = table.getSelectedRow();
				try {
					upDate();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
//		btnReadMusicInfo.setIcon(new ImageIcon("AudioIcons/cloud-data.png"));
		btnReadMusicInfo.setIcon(new ImageIcon(getClass().getClassLoader().getResource("cloud-data.png")));
		btnReadMusicInfo.setBounds(401, 0, 48, 44);
		panelHeader.add(btnReadMusicInfo);

		// 用户用这个按钮从本地来的音乐以及他的路径存储到数据库上
		btnUploadMusicToDatabase = new JButton("");
		btnUploadMusicToDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.print("文件路径 - " + path);
				System.out.print("歌名 " + f.getName());
				File f = new File(path);
				try {
					InputStream is = new FileInputStream(f);
//					Class.forName("com.mysql.jdbc.Driver"); // 启动我的驱动程序]
					sqlConn = DriverManager.getConnection(dataConn, username, password);
					pst = sqlConn.prepareStatement("insert into music_test(musicname, musicpath)value(?,?)");
//					java.io.InputStream in=new FileInputStream(is);		
					pst.setBinaryStream(2, is, (int) f.length());

					pst.setString(1, f.getName());
//					pst.setString(2, textLang.getText());
//					pst.setString(3, textCategory.getText());
//					pst.setString(4, textSinger.getText());
					pst.setString(2, path);
//					pst.setBlob(6, is);

//					pst.executeUpdate();
//					JOptionPane.showMessageDialog(btnAddNew, "增加成功");

					// 4.操作数据库
					int inserted = pst.executeUpdate();

					if (inserted > 0) {
						JOptionPane.showMessageDialog(btnUploadMusicToDatabase, "增加成功");
						upDate(); // 加载
						System.out.println("操作成功！");
					}
//					JOptionPane.showMessageDialog(jbtnAddNew, this, "增加成功", deleteItem, null);

				} catch (SQLException ex) {
					java.util.logging.Logger.getLogger(Music_MysqlConn.class.getName())
							.log(java.util.logging.Level.SEVERE, null, ex);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					try {
						if (pst != null)
							pst.close();
						if (sqlConn != null)
							sqlConn.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

//		btnUploadMusicToDatabase.setIcon(new ImageIcon("AudioIcons/upload.png"));
		btnUploadMusicToDatabase.setIcon(new ImageIcon(getClass().getClassLoader().getResource("upload.png")));
		btnUploadMusicToDatabase.setBounds(464, 0, 48, 44);
		panelHeader.add(btnUploadMusicToDatabase);
		btnToDatabases = new JButton("");

		// 这个按钮去增删改查界面
		btnToDatabases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Music_MysqlConn music_MysqlConn = new Music_MysqlConn();
				music_MysqlConn.frame.setVisible(true);

			}
		});
//		btnToDatabases.setIcon(new ImageIcon("AudioIcons/cloud.png"));
		btnToDatabases.setIcon(new ImageIcon(getClass().getClassLoader().getResource("cloud.png")));
		btnToDatabases.setBounds(525, 0, 48, 44);
		panelHeader.add(btnToDatabases);
//		btnToLogin.setIcon(new ImageIcon("AudioIcons/home (2).png"));
		btnToLogin.setIcon(new ImageIcon(getClass().getClassLoader().getResource("home (2).png")));
		btnToLogin.setBounds(585, 0, 48, 44);
		panelHeader.add(btnToLogin);

		/*
		 * 把歌曲从本地上传到去播放按钮
		 */
		btnUploadMusic = new JButton("");
		btnUploadMusic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				player.setRepeat(repeat); /// 重复
				JFileChooser fileChooser = new JFileChooser(currentDirectory);
				fileChooser.setFileFilter(new FileTypeFilter(".mp3", "只选择MP3文件!"));
				int load = fileChooser.showOpenDialog(null);

				if (load == JFileChooser.APPROVE_OPTION) {
					// 调用文件选择器
					f = fileChooser.getSelectedFile();
					songFile = fileChooser.getSelectedFile();
					// 打开文件选择器选择了

					player.addToPlayList(songFile);

					// 并使用播放器的s立方体前进方法播放新添加的音乐
					player.skipForward();
//					fillProgressbar();
					// 当前目录 的歌曲 文件绝对目录

					currentDirectory = songFile.getAbsolutePath();
					lblMusicNameDysplay.setText("正在播放... | " + songFile.getName()); // 目录和歌曲显示我们的文件名
				}

			}
		});
		btnUploadMusic.addMouseListener(new MouseAdapter() {

		});
//		btnUploadMusic.setIcon(new ImageIcon("AudioIcons/upload.png"));
		btnUploadMusic.setIcon(new ImageIcon(getClass().getClassLoader().getResource("upload.png")));
		btnUploadMusic.setBounds(18, 45, 48, 44);
		panelControlButtons.add(btnUploadMusic);

		// 暂停
		btnPause = new JButton("");
		btnPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 鼠标三级事件 暂停按钮
				// 并使用暂停方法
				player.pause();
			}
		});

		// 上一首歌
		btnPreviouse = new JButton("");
		btnPreviouse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel RecordTable = (DefaultTableModel) table.getModel();
				int SelectedRows = table.getSelectedRow();
//				SelectedRows.
//				textMusicNamePath.
				try {
					// 1.加载驱动类
					Class.forName("com.mysql.cj.jdbc.Driver"); // 启动我的驱动程序]
					// 2.连接数据库
					sqlConn = DriverManager.getConnection(dataConn, username, password);
					// 3.获取操作数据库对象
					String sql = "select * from music_test where id=?";
					pst = sqlConn.prepareStatement(sql);
					pst.setInt(1, 1);
					// 4.操作数据库
					rs = pst.executeQuery();
					// 5.处理结果集
					if (rs.next() != false) {
						InputStream in = rs.getBinaryStream(SelectedRows);
						OutputStream out = new FileOutputStream(path);

						byte[] chs = new byte[100];
						int len = -1;
						while ((len = in.read(chs)) != -1) {
							out.write(chs, 0, len);
						}
						out.close();
						in.close();
					}
					System.out.println("操作成功！");
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					try {
						if (pst != null)
							pst.close();
						if (sqlConn != null)
							sqlConn.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

				// 添加鼠标单击事件 播放按钮并使用播放器的播放方法啊
//				player.play();
				if (e.getSource() == btnPlay) {
					int rowIndex = table.getSelectedRow();
					if (rowIndex < 0) {
//					player.add(btnPlay);
						player.addToPlayList(f);
						player.skipForward();
//					lblSongNameDysplay.setText("正在播放... | " + songFile.getName()); // 目录和歌曲显示我们的文件名
//						  JOptionPane.showInputDialog(this, "未选择待删除的记录!");
						player.play();
					}
				}
			}
		});
//		btnPreviouse.setIcon(new ImageIcon("AudioIcons/previous.png"));
		btnPreviouse.setIcon(new ImageIcon(getClass().getClassLoader().getResource("previous.png")));
		btnPreviouse.setBounds(67, 45, 48, 44);
		panelControlButtons.add(btnPreviouse);

//		btnPause.setIcon(new ImageIcon("AudioIcons/play-button (1).png"));
		btnPause.setIcon(new ImageIcon(getClass().getClassLoader().getResource("play-button (1).png")));
		btnPause.setBounds(117, 45, 48, 44);
		panelControlButtons.add(btnPause);

		// 开始播放
		btnPlay = new JButton("");
		btnPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// 添加鼠标单击事件 播放按钮并使用播放器的播放方法啊
				player.play();

			}
		});

//		btnPlay.setIcon(new ImageIcon("AudioIcons/play-button.png"));
		btnPlay.setIcon(new ImageIcon(getClass().getClassLoader().getResource("play-button.png")));
		btnPlay.setBounds(167, 45, 48, 44);
		panelControlButtons.add(btnPlay);

		// 停止音乐按钮
		btnStop = new JButton("");
		btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 鼠标单击事件停止按钮 并使用播放器的停止方法

				try {

					strPath = "";
					player.stop();
//					listModel.removeAllElements();
				} catch (Exception e1) {
				}

			}
		});
//		btnStop.setIcon(new ImageIcon("AudioIcons/stop (1).png"));
		btnStop.setIcon(new ImageIcon(getClass().getClassLoader().getResource("stop (1).png")));
		btnStop.setBounds(267, 45, 48, 44);
		panelControlButtons.add(btnStop);

		// 下一首歌
		btnNext = new JButton("");
//		btnNext.setIcon(new ImageIcon("AudioIcons/next-button.png"));
		btnNext.setIcon(new ImageIcon(getClass().getClassLoader().getResource("next-button.png")));
		btnNext.setBounds(216, 45, 48, 44);
		panelControlButtons.add(btnNext);

		// 打开文件夹选择音乐播放
		btnOpenFile = new JButton("");

		btnOpenFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/// zhaki1
				// 文件选择器
				// 现在使用当前目录制作文件选择
				JFileChooser fileChooser = new JFileChooser(currentDirectory);
				FileNameExtensionFilter fnwf = new FileNameExtensionFilter("只选择.MP3", "mp3");

				// 文件选择器
				fileChooser.addChoosableFileFilter(fnwf);
				int load = fileChooser.showOpenDialog(null);

				// 文件类型过滤器 是新的文件类型过滤器

				if (load == fileChooser.APPROVE_OPTION) {
//					selectedFiles = fileChooser.getSelectedFiles();
					f = fileChooser.getSelectedFile();

					path = f.getAbsolutePath();
					textMusicNamePath.setText(path); // JTextField
				}

			}
		});
//		btnOpenFile.setIcon(new ImageIcon("AudioIcons/folder.png"));
		btnOpenFile.setIcon(new ImageIcon(getClass().getClassLoader().getResource("folder.png")));
		btnOpenFile.setBounds(317, 45, 48, 44);
		panelControlButtons.add(btnOpenFile);

		/*
		 * 音量调满按钮
		 */
		btnVolumeFull = new JButton("");
		btnVolumeFull.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				volumeControl(1.0);
				// 调用音量控制方法 , 参数1.1设置全音量
			}
		});

		/*
		 * 调小
		 */
		btnVolumeDown = new JButton("");
		btnVolumeDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 减小按钮调用音量 减小控制方法 并且 赋值0.1 他的参数每次点
				volumeDownControl(0.1);
			}
		});

//		btnVolumeDown.setIcon(new ImageIcon("AudioIcons/volume-down.png"));
		btnVolumeDown.setIcon(new ImageIcon(getClass().getClassLoader().getResource("volume-down.png")));
		btnVolumeDown.setBounds(367, 45, 48, 44);
		panelControlButtons.add(btnVolumeDown);
		btnVolumeUp = new JButton("");
		// 调大
		btnVolumeUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 调大按钮 调用音量增大控制方法
				// 设置0.1值作为他的参数减少0.1每次点击增加
				volumeUpControl(0.1);
			}
		});

//		btnVolumeUp.setIcon(new ImageIcon("AudioIcons/volume-up (1).png"));
		btnVolumeUp.setIcon(new ImageIcon(getClass().getClassLoader().getResource("volume-up (1).png")));
		btnVolumeUp.setBounds(417, 45, 48, 44);
		panelControlButtons.add(btnVolumeUp);
		// 调满
//		btnVolumeFull.setIcon(new ImageIcon("AudioIcons/sound.png"));
		btnVolumeFull.setIcon(new ImageIcon(getClass().getClassLoader().getResource("sound.png")));
		btnVolumeFull.setBounds(467, 45, 48, 44);
		panelControlButtons.add(btnVolumeFull);

		/*
		 * 静音按钮
		 */
		btnMute = new JButton("");
		btnMute.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 静音按钮调用音量控制方法
				volumeControl(0.0);
			}
		});
//		btnMute.setIcon(new ImageIcon("AudioIcons/mute.png"));
		btnMute.setIcon(new ImageIcon(getClass().getClassLoader().getResource("mute.png")));
		btnMute.setBounds(517, 45, 48, 44);
		panelControlButtons.add(btnMute);

		/*
		 * 切换到我的第一个做的页面
		 */
		btnChangeToOldIntr = new JButton("");
		btnChangeToOldIntr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				PlayerFrame playerFrame = new PlayerFrame();
				playerFrame.frame.setVisible(true);
			}
		});

//		btnChangeToOldIntr.setIcon(new ImageIcon("AudioIcons/filter.png"));
		btnChangeToOldIntr.setIcon(new ImageIcon(getClass().getClassLoader().getResource("filter.png")));
		btnChangeToOldIntr.setBounds(567, 45, 48, 44);
		panelControlButtons.add(btnChangeToOldIntr);

//		funList();

	}

	protected void dispose() {
		// TODO Auto-generated method stub

	}

	// 创建一个返回mp3播放器的方法,
	private MP3Player mp3Player() {
		MP3Player mp3Player = new MP3Player();
		return mp3Player;
	}

	/**
	 * 用户可以自己输入URL地址, 去访问那个网站的方法
	 */
	private void urlOpener(String url) {
		// TODO Desktop类允许一个Java程序启动本地的另一个应用程序去处理URI(URL的一个父类)(URL是URI一个子集合)或文件请求
		Desktop desktop = Desktop.getDesktop();
		try {
			if (desktop.isSupported(Desktop.Action.OPEN)) {
				// 如果支持桌面, 我们打开URL
				desktop.browse(java.net.URI.create(url));
			} else {
				Process p;
				try {
					p = Runtime.getRuntime().exec("cmd / c start " + url);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} catch (IOException ex) {
			Logger.getLogger(Music_MysqlConn.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

//	// 进度条
	public void fillProgressbar() {
		int counter = 0;
		while (counter <= 100) {
			progressBar.setValue(counter); // 进度条随着时间的推移而增加

			try {
				Thread.sleep(1000); // 1000毫秒, 程序暂停1秒, 循环的迭代
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			counter += 1;

		}
		progressBar.setString("DONE! :)");
	}

	public void stopPlayer() {
		try {
			strPath = "";
			listModel.removeAllElements();
		} catch (Exception e) {
		}
	}
	
	/*
	 *  Line	getLine(Line.Info info)
          获得可供使用并且与指定 Line.Info 对象中的描述匹配的行。
 int	getMaxLines(Line.Info info)
          获得可以在此混频器上同时打开的所请求类型的行的最大近似数。
 Mixer.Info	getMixerInfo()
          获得关于此混频器的信息，包括产品名称、版本、供应商，等等。
 Line.Info[]	getSourceLineInfo()
          获得关于此混频器支持的源行集合的信息。
 Line.Info[]	getSourceLineInfo(Line.Info info)
          获得关于此混频器支持的特定类型源行的信息。
 Line[]	getSourceLines()
          获得当前对此混频器打开的所有源行的集合。
 Line.Info[]	getTargetLineInfo()
          获得关于此混频器支持的目标行集合的信息。
 Line.Info[]	getTargetLineInfo(Line.Info info)
          获得关于此混频器支持的特定类型目标行的信息。
 Line[]	getTargetLines()
          获得当前从此混频器打开的所有目标行的集合。
 boolean	isLineSupported(Line.Info info)
          指示混频器是否支持与指定 Line.Info 对象匹配的一行（或多行）。
 boolean	isSynchronizationSupported(Line[] lines, boolean maintainSync)
          报告此混频器是否支持指定行集合的同步。
 void	synchronize(Line[] lines, boolean maintainSync)
          同步两个或多个行。
 void	unsynchronize(Line[] lines)
          释放指定行的同步。
	 */
	/**
	 * 
	 * @param valueToPlusMinus
	 * 
	 * 通过Mixer扩展的Line接口的方法，混音器可以提供一组对混音器来说是全局的控制。
	 * 例如，混音器可以有一个主增益控制。这些全局控制与属于混音器各个线路的控制是不同的。
	 */
	// 将这个方法复制2次，重命名为volumeUpControl和volumeControl。
	//-------------------------------------------------------------------------------
	// 音量控制方法 并制作双精度
	// 一种获取音频系统和混音器信息的方法
	// 自己的值设置为目标混音器
	private void volumeDownControl(Double valueToPlusMinus) {
		// 从音频系统中获取调音台信息
		//获得关于此混频器的信息，包括产品名称、版本、供应商，等等
		Mixer.Info[] mixers = AudioSystem.getMixerInfo();
		// 现在使用for循环来列出所有混音器
		for (Mixer.Info mixerInfo : mixers) {
			// 获得混合器。
			Mixer mixer = AudioSystem.getMixer(mixerInfo);
			// 现在得到目标线
			Line.Info[] lineInfos = mixer.getTargetLineInfo();
			// 这里再次使用for循环来列出
			for (Line.Info lineInfo : lineInfos) {
				Line line = null;
				// 做一个布尔值作为打开
				boolean opened = true;
				// 现在使用try异常来打开一个行
				try {
					line = mixer.getLine(lineInfo);
					opened = line.isOpen() || line instanceof Clip;

					// 现在检查线路是否未被打开
					if (!opened) {
						// 打开 line
						line.open();
					}

					// 做一个浮动的控制
					FloatControl volControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);

					// 获取当前音量
					float currentVolume = volControl.getValue();

					// 做一个临时的双倍变量，并存储值加减
					Double volumeToCut = valueToPlusMinus;
					// 做一个浮点数，计算体积的加减
					float changedCalc = (float) ((float) currentVolume - (double) volumeToCut);
					// 现在将这个改变的值设置为音量线
					volControl.setValue(changedCalc);
				} catch (LineUnavailableException lineException) {

				} catch (IllegalArgumentException illegalArgumentException) {

				} finally {
					// 关闭线路（如果已打开）
					if (line != null && !opened) {
						line.close();
					}
				}

			}
		}
	}

	// ----------------------------------------------------------------------------------
	/**
	 * 2 设置音量增加的方法
	 * 
	 * @param valueToPlusMinus
	 */
	// 一种获取音频系统和混音器信息的方法
	private void volumeUpControl(Double valueToPlusMinus) {

		Mixer.Info[] mixers = AudioSystem.getMixerInfo();
		for (Mixer.Info mixerInfo : mixers) {
			Mixer mixer = AudioSystem.getMixer(mixerInfo);
			Line.Info[] lineInfos = mixer.getTargetLineInfo();
			for (Line.Info lineInfo : lineInfos) {
				Line line = null;
				boolean opened = true;

				try {
					line = mixer.getLine(lineInfo);
					opened = line.isOpen() || line instanceof Clip;

					if (!opened) {
						line.open();
					}

					FloatControl volControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);

					float currentVolume = volControl.getValue();
					Double volumeToCut = valueToPlusMinus;

					float changedCalc = (float) ((float) currentVolume + (double) volumeToCut);

					volControl.setValue(changedCalc);
				} catch (LineUnavailableException lineException) {

				} catch (IllegalArgumentException illegalArgumentException) {

				} finally {
					if (line != null && !opened) {
						line.close();
					}
				}

			}
		}
	}

	// _______------___--------------------------------------------------------------------------

	// 3
	// 制作音量制作方法 并制作双精度
	// 一种获取音频系统和混音器信息的方法
	private void volumeControl(Double valueToPlusMinus) {
		Mixer.Info[] mixers = AudioSystem.getMixerInfo();
		for (Mixer.Info mixerInfo : mixers) {
			Mixer mixer = AudioSystem.getMixer(mixerInfo);
			Line.Info[] lineInfos = mixer.getTargetLineInfo();
			for (Line.Info lineInfo : lineInfos) {
				Line line = null;
				boolean opened = true;
				try {
					line = mixer.getLine(lineInfo);
					opened = line.isOpen() || line instanceof Clip;
					if (!opened) {
						line.open();
					}
					FloatControl volControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
					float currentVolume = volControl.getValue();
					Double volumeToCut = valueToPlusMinus;
					float changedCalc = (float) ((double) volumeToCut);
					volControl.setValue(changedCalc);
				} catch (LineUnavailableException lineException) {

				} catch (IllegalArgumentException illegalArgumentException) {

				} finally {
					if (line != null && !opened) {
						line.close();
					}
				}

			}
		}
	}
}
