package dp_2020L038;

import java.awt.EventQueue;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.spi.CurrencyNameProvider;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import jaco.mp3.player.MP3Player;

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
import java.awt.event.MouseMotionAdapter;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.ScrollPaneConstants;

/**
 * 
 * @author zhaki
 * @version 1.0 在这PlayerFrame类可以单独的运行看效果, 功能包括多选歌曲导入到列表上, 可以播放, 暂停, 停止, 音量🔊调满,
 *          调大, 调小, 静音, 可以
 */
public class PlayerFrame {

	public JFrame frame; // 窗体
	private JPanel mainPanel; // 主面板
	private JPanel panelListMain; // JList 的主面板

	JPanel subPanelList;
	JScrollPane scrollPane_1;

	JPanel panelControl;
	JProgressBar progressBar;

	/**
	 * 音乐管理按钮
	 */
	private JButton btnUploadMusic;
	private JButton btnPreviouse;
	private JButton btnPause;
	private JButton btnPlay;
	private JButton btnNext;
	private JButton btnStop;
	private JButton btnOpenFile;
	private JButton btnVolumeDown;
	private JButton btnVolumUp;
	private JButton btnVolumeFull;
	private JButton btnMute;

	// 正在播放音乐面板以及子面板
	private JPanel panelMainSongName;
	private JPanel subPanelSongName;
	private JLabel lblSongNameDysplay;

	private JPanel animationPanel; // GIF图片的主面板
	private JPanel subAnimationPanel; // 这个他的子面板
	private JLabel lblNewLabel; // 在JLabel上设置动态的GIF图片

	private JPanel panelHeader; // 给我的Logo面板
	private JLabel lblTitle; // MP3播放器标题以及设置他的Logo图片
	private JButton btnexit;
	private JButton btnSettings;

//	JLabel songNameDisplay;

	/*
	 * 编写代码开始之前, 必须添加mp3播放器库
	 */
	/*
	 * 定义一些通用的变量 JACO MP3Player Lib
	 */
	MP3Player player;

	String appName = "MP3播放器";
	/*
	 * 文件以使播放器文件强大, 可以存储歌曲的位置的文件
	 */
	File songFile;

	/*
	 * 字符串作为当前的目录 这将是默认的
	 * 
	 */
	String currentDirectory = "home/user";
	// 当前路径这可以获得我们的应用程序运行路径
	String currentPath;

	// Here i am making a boolean for windowCollapsed
	boolean windowCollapsed = false;
	// 检查窗口是否收集

	// Here i used to define mouse x and y for to get mouse position on screen
	// 获取鼠标的位置
	int xMouse, yMouse;
	JScrollPane scrollPane;
//	DefaultListModel<String> listModel;
//	JList<String> list_1;

	long pauseLoc, songLength;
	int playstatus = 0, filepathresponse, trackNo = 0;

	FileInputStream fis1;

	BufferedInputStream bis1;
//	JFileChooser fcPath = new JFileChooser();
	String strPath = "", strPathNew;
	FileNameExtensionFilter filter;
//	MarqueeLabel lblCurrentSong;
	int width = 600, height = 410;

	// *********

	private DefaultListModel<String> listModel;
	JList<String> list_1 = new JList<>(listModel = new DefaultListModel<String>());

	JFileChooser fcPath = new JFileChooser();
	File[] selectedFiles;

	/**
	 * 主方法单独运行这个界面
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					PlayerFrame window = new PlayerFrame();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public PlayerFrame() {
		initialize();
		songFile = new File("/Users/zhaki/Desktop/Music");
		String fileName = songFile.getName();

		// 播放器中初始化该方法并
		player = mp3Player();

		// 将歌曲文件添加到播放器播放 列表
		player.addToPlayList(songFile);
		// 将应用程序路径放入我们当前的路径
		currentPath = Paths.get(".").toAbsolutePath().normalize().toString();

	}

	private void initialize() {
		// 设置窗口
		frame = new JFrame();
		frame.setPreferredSize(new Dimension(250, 0));
		frame.setBounds(100, 100, 654, 560);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null); // 中心
		frame.setUndecorated(false);
		frame.setResizable(false);// 大小不能该

		// 设置主面板
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBackground(new Color(20, 40, 80));
		mainPanel.setBounds(0, 0, 654, 532);
		frame.getContentPane().add(mainPanel);

		// JList列表的主面板
		panelListMain = new JPanel();
		panelListMain.setLayout(null);
		panelListMain.setBackground(new Color(20, 40, 80));
		panelListMain.setBounds(10, 62, 312, 301);
		mainPanel.add(panelListMain);

		// JList列表的子面板
		subPanelList = new JPanel();
		subPanelList.setBounds(6, 6, 300, 289);
		panelListMain.add(subPanelList);
		subPanelList.setLayout(null);
		subPanelList.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		subPanelList.setBackground(new Color(20, 40, 80));

		// JList的滚动面板
		scrollPane_1 = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(6, 6, 288, 277);

		Border blackline = BorderFactory.createLineBorder(Color.black);
		scrollPane_1.setBorder(blackline);
		list_1.setFont(new Font("Lucida Grande", Font.BOLD, 13));

		list_1.setForeground(new Color(135, 206, 250));
		list_1.setBackground(new Color(0, 0, 0));
		list_1.setSize(286, 275);
//				 ListSelectionListener listSelectionListener = new ListSelectionListener() 
		list_1.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {// This line prevents double events
//			        		jumpTrack(list.getSelectedIndex());
					list_1.setSelectionBackground(new Color(255, 128, 0));
				}
			}
		});
//		scrollPane_1.setViewportView(list_1);
		subPanelList.add(scrollPane_1);
		scrollPane_1.add(list_1);

		/**
		 * 音乐管理的那些按钮的面板
		 */
		panelControl = new JPanel();
		panelControl.setLayout(null);
		panelControl.setBackground(new Color(20, 40, 80));
		panelControl.setBounds(10, 431, 634, 95);
		mainPanel.add(panelControl);

		/**
		 * 进度条
		 */
		progressBar = new JProgressBar();
		progressBar.setForeground(new Color(0, 191, 255));
		progressBar.setFont(new Font("Libian SC", Font.BOLD, 13));

		progressBar.setBounds(41, 13, 547, 20);
		progressBar.setValue(2);

//		progressBar.setStringPainted(true);
		panelControl.add(progressBar);
		progressBar.setBackground(new Color(0, 0, 0));

		/**
		 * 正在播放音乐面板以及子面板
		 */
		panelMainSongName = new JPanel();
		panelMainSongName.setBounds(10, 375, 634, 44);
		mainPanel.add(panelMainSongName);
		panelMainSongName.setLayout(null);

		subPanelSongName = new JPanel();
		subPanelSongName.setBackground(new Color(0, 0, 0));
		subPanelSongName.setBounds(6, 6, 622, 32);
		panelMainSongName.add(subPanelSongName);
		subPanelSongName.setLayout(null);

		// 显示正在播放的音乐
		lblSongNameDysplay = new JLabel("   要听音乐🎵吗?");
		lblSongNameDysplay.setIconTextGap(1);
//		lblSongNameDysplay.setIcon(new ImageIcon("AudioIcons/gifgit.gif"));
		lblSongNameDysplay.setIcon(new ImageIcon(getClass().getClassLoader().getResource("gifgit.gif")));
		lblSongNameDysplay.setForeground(new Color(135, 206, 250));
		lblSongNameDysplay.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblSongNameDysplay.setHorizontalAlignment(SwingConstants.CENTER);
		lblSongNameDysplay.setBounds(6, 6, 610, 20);
		subPanelSongName.add(lblSongNameDysplay);

		/**
		 * 动态的GIF 图片的面板, 子面板, 以及设置他的图片
		 */
		animationPanel = new JPanel();
		animationPanel.setLayout(null);
		animationPanel.setBackground(new Color(20, 40, 80));
		animationPanel.setBounds(332, 62, 312, 301);
		mainPanel.add(animationPanel);

		subAnimationPanel = new JPanel();
		subAnimationPanel.setLayout(null);
		subAnimationPanel.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		subAnimationPanel.setBackground(new Color(0, 0, 0));
		subAnimationPanel.setBounds(6, 6, 300, 289);
		animationPanel.add(subAnimationPanel);

		lblNewLabel = new JLabel("");
		lblNewLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
//				String click;
//				lblNewLabel.setIcon(new ImageIcon("AudioIcons/mybestdancer.gif"));
				lblNewLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("mybestdancer.gif")));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("你释放了鼠标 - ");
//				lblNewLabel.setIcon(new ImageIcon("AudioIcons/gifMusic2.gif"));
				lblNewLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("gifMusic2.gif")));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("你按了鼠标");
//				lblNewLabel.setIcon(new ImageIcon("AudioIcons/gifMusic2.gif"));
				lblNewLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("gifMusic2.gif")));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				System.out.println("你把这个部件放进去了-");
//				lblNewLabel.setIcon(new ImageIcon("AudioIcons/musicGif.gif"));
				lblNewLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("musicGif.gif")));
			}
		});
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		lblNewLabel.setIcon(new ImageIcon("AudioIcons/animated5.gif"));
		lblNewLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("animated5.gif")));
		lblNewLabel.setBounds(6, 6, 288, 277);
		subAnimationPanel.add(lblNewLabel);

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
		lblTitle.setBounds(6, 6, 507, 38);
		panelHeader.add(lblTitle);

		/*
		 * 按钮返回到登录与注册界面
		 */
		btnexit = new JButton("");
		btnexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Login login = new Login();
				login.frame.setLocationRelativeTo(null);
				login.frame.setVisible(true);

			}
		});
//		btnexit.setIcon(new ImageIcon("AudioIcons/home (2).png"));
		btnexit.setIcon(new ImageIcon(getClass().getClassLoader().getResource("home (2).png")));
		btnexit.setBounds(585, 0, 48, 44);
		panelHeader.add(btnexit);

		// 打开数据库增删改界面
		btnSettings = new JButton("");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Music_MysqlConn music_MysqlConn = new Music_MysqlConn();
				music_MysqlConn.frame.setVisible(true);

			}
		});
//		btnSettings.setIcon(new ImageIcon("AudioIcons/cloud.png"));
		btnSettings.setIcon(new ImageIcon(getClass().getClassLoader().getResource("cloud.png")));
		btnSettings.setBounds(525, 0, 48, 44);
		panelHeader.add(btnSettings);

		btnUploadMusic = new JButton("");
		btnUploadMusic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				player.setRepeat(repeat); /// 重复
				JFileChooser openFileChooser = new JFileChooser(currentDirectory);
				openFileChooser.setFileFilter(new FileTypeFilter(".mp3", "只选择MP3文件!"));
				int result = openFileChooser.showOpenDialog(null);

				if (result == JFileChooser.APPROVE_OPTION) {
					songFile = openFileChooser.getSelectedFile();
					// 打开文件选择器选择了

					player.addToPlayList(songFile);

					// 并使用播放器的s立方体前进方法播放新添加的音乐
					player.skipForward();
//					fillProgressbar();
					// 当前目录 的歌曲 文件绝对目录

					currentDirectory = songFile.getAbsolutePath();
					lblSongNameDysplay.setText("正在播放... | " + songFile.getName()); // 目录和歌曲显示我们的文件名
				}

			}
		});
		btnUploadMusic.addMouseListener(new MouseAdapter() {

		});
//		btnUploadMusic.setIcon(new ImageIcon("AudioIcons/upload.png"));
		btnUploadMusic.setIcon(new ImageIcon(getClass().getClassLoader().getResource("upload.png")));
		btnUploadMusic.setBounds(41, 45, 48, 44);
		panelControl.add(btnUploadMusic);

		btnPause = new JButton("");
		btnPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 鼠标三级事件 暂停按钮
				// 并使用暂停方法
				player.pause();
			}
		});

		btnPreviouse = new JButton("");
//		btnPreviouse.setIcon(new ImageIcon("AudioIcons/previous.png"));
		btnPreviouse.setIcon(new ImageIcon(getClass().getClassLoader().getResource("previous.png")));
		btnPreviouse.setBounds(90, 45, 48, 44);
		panelControl.add(btnPreviouse);

//		btnPause.setIcon(new ImageIcon("AudioIcons/play-button (1).png"));
		btnPause.setIcon(new ImageIcon(getClass().getClassLoader().getResource("play-button (1).png")));
		btnPause.setBounds(140, 45, 48, 44);
		panelControl.add(btnPause);

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
		btnPlay.setBounds(190, 45, 48, 44);
		panelControl.add(btnPlay);

		btnStop = new JButton("");
		btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 鼠标单击事件停止按钮 并使用播放器的停止方法

				try {
//					playstatus = 0;
					strPath = "";

					player.stop();
//					listModel.removeAllElements();
				} catch (Exception e1) {
				}

			}
		});
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		btnNext = new JButton("");
//		btnNext.setIcon(new ImageIcon("AudioIcons/next-button.png"));
		btnNext.setIcon(new ImageIcon(getClass().getClassLoader().getResource("next-button.png")));
		btnNext.setBounds(239, 45, 48, 44);
		panelControl.add(btnNext);
//		btnStop.setIcon(new ImageIcon("AudioIcons/stop (1).png"));
		btnStop.setIcon(new ImageIcon(getClass().getClassLoader().getResource("stop (1).png")));
		btnStop.setBounds(290, 45, 48, 44);
		panelControl.add(btnStop);

		btnOpenFile = new JButton("");
		btnOpenFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/// zhaki1
				// 文件选择器
				// 现在使用当前目录制作文件选择
				JFileChooser fileChooser = new JFileChooser(currentDirectory);

				// 文件类型过滤器 是新的文件类型过滤器
				fileChooser.setFileFilter(new FileTypeFilter(".mp3", "只选择MP3文件"));
				// TODO Auto-generated method stub
				fileChooser.setMultiSelectionEnabled(true);
				int result = fileChooser.showOpenDialog(null);

				if (result == JFileChooser.APPROVE_OPTION) {
					selectedFiles = fileChooser.getSelectedFiles();

//				int result = fileChooser.showOpenDialog(null);
					for (File f : selectedFiles) {
//					if (result == JFileChooser.APPROVE_OPTION) {

//					songFile = openFileChooser.getSelectedFiles();
						listModel.addElement(f.getName());

//					}
					}
				}
//
//				}
			}
		});
//		btnOpenFile.setIcon(new ImageIcon("AudioIcons/folder.png"));
		btnOpenFile.setIcon(new ImageIcon(getClass().getClassLoader().getResource("folder.png")));
		btnOpenFile.setBounds(340, 45, 48, 44);
		panelControl.add(btnOpenFile);

		btnVolumeFull = new JButton("");
		btnVolumeFull.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				volumeControl(1.0);
				// 调用音量控制方法 , 参数1.1设置全音量
			}
		});

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
		btnVolumeDown.setBounds(390, 45, 48, 44);
		panelControl.add(btnVolumeDown);

		btnVolumUp = new JButton("");
		btnVolumUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 调大按钮 调用音量增大控制方法
				// 设置0.1值作为他的参数减少0.1每次点击增加
				volumeUpControl(0.1);

			}
		});

//		btnVolumUp.setIcon(new ImageIcon("AudioIcons/volume-up (1).png"));
		btnVolumUp.setIcon(new ImageIcon(getClass().getClassLoader().getResource("volume-up (1).png")));
		btnVolumUp.setBounds(440, 45, 48, 44);
		panelControl.add(btnVolumUp);
//		btnVolumeFull.setIcon(new ImageIcon("AudioIcons/sound.png"));
		btnVolumeFull.setIcon(new ImageIcon(getClass().getClassLoader().getResource("sound.png")));
		btnVolumeFull.setBounds(490, 45, 48, 44);
		panelControl.add(btnVolumeFull);

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
		btnMute.setBounds(540, 45, 48, 44);
		panelControl.add(btnMute);

//		funList();

	}

	protected void dispose() {
		// TODO Auto-generated method stub

	}

	// 2 to create a custom MP3Player Method
	// 创建一个返回mp3播放器的方法,
	private MP3Player mp3Player() {
		MP3Player mp3Player = new MP3Player();
		return mp3Player;
	}

//	// 进度条
	public void fillProgressbar() {
		// установить значение
//		bar.setValue(10); // оно сразу же будет установлено 10
		int counter = 0;
		while (counter <= 100) {
			progressBar.setValue(counter); // 进度条随着时间的推移而增加

			try {
				Thread.sleep(1000); // 1000毫秒, 程序暂停1秒, 循环的迭代
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// увеличим наш счетчик на 100%
			counter += 1;
		}
		// можем добавить тест на наш bar панель
		progressBar.setString("DONE! :)");
	}

	// 1
	// 制作音量控制方法 并制作双精度

	// 将这个方法复制2次，重命名为volumeUpControl和volumeControl。
	// _______------___--------------------------------------------------------------------------
	// 音量控制方法 并制作双精度
	// 一种获取音频系统和混音器信息的方法
	// 自己的值设置为目标混音器
	private void volumeDownControl(Double valueToPlusMinus) {
		// 从音频系统中获取调音台信息
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

	// _______------___--------------------------------------------------------------------------s

	// _______------___--------------------------------------------------------------------------
	// _______------___--------------------------------------------------------------------------
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

	// 制作音量制作方法 并制作双精度
	// 一种获取音频系统和混音器信息的方法
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
	// _______------___--------------------------------------------------------------------------

}
