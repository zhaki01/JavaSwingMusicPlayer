package dp_2020L038;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Window.Type;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
//import java.io.ClassNotFoundException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

//import JFrameSQLCustomers.CustomerDAO;

import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.ScrollPane;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.MessageFormat;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/***
 * 
 * @author zhakiDapeng
 * @version 1.0 Music_MysqlConn 增删改查
 */
public class Music_MysqlConn {

	// 窗体, 主面板, 子面板
	public JFrame frame;
	private JPanel panelMain;
	private JPanel panelButtons;

	// 在子面板上的那些按钮
	private JButton btnAddNew;
	private JButton btnUpdate;
	private JButton btnRest;
	private JButton btnDelete;
	private JButton btnExit;
	private JButton btnInfo;
	private JButton btnUpload;
	private JLabel lbliconinbtn;

	// 界面上面Logo和标题
	private JPanel panelHeader;
	private JLabel lblTitle;

	/**
	 * panelTable面板上设置JTable, 以及给用户显示的标签, 文本框
	 */
	private JPanel panelTable;
	private JLabel lblid_music;
	private JTextField textID;
	private JLabel lblname_music;
	private JTextField textNameMusic;
	private JLabel lblLanguage_music_1;
	private JTextField textLang;
	private JLabel lblCategory_music;
	private JTextField textCategory;
	private JLabel lblSinger_music;
	private JTextField textSinger;
	private JLabel lblfilePath_music;
	private JTextField textSongNamePath;
	private JLabel lblSearch_music;
	private JTextField textSerch;

	private JButton btnSearch;

	// 设置JTable以及滚动面板
	private JScrollPane scrollPane_1;
	private JTable table;

//	private JTextField textSongName;

	private JScrollPane scrollPane;
	DefaultTableModel model;
//	CustomerDAO customerDAO = new CustomerDAO();

	File f = null;

	String path = null;
	private ImageIcon format = null;

	// 数据库
	private static final String username = "root";
	private static final String password = "dapeng";
	private static final String dataConn = "jdbc:mysql://localhost/MusicDatabase";
	private static final String driver = "com.mysql.jdbc.Driver";
	protected static final String SelectedRows = null;
	// 连接
	Connection sqlConn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	int q, i, num, deleteItem;
	int numUpdate;

//	String name;
//	public Java_MysqlConn() {
//		initComponents();
////	}
	protected int update;

	// ========================刷新函数==========================
	public void upDate() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); // 启动我的驱动程序]
			sqlConn = DriverManager.getConnection(dataConn, username, password);
			pst = sqlConn.prepareStatement("select * from music_table");

			// 结果
			rs = pst.executeQuery();
			ResultSetMetaData stData = rs.getMetaData();
			q = stData.getColumnCount(); // lie

			DefaultTableModel RecordTable = (DefaultTableModel) table.getModel();
			RecordTable.setRowCount(0);

			while (rs.next()) {
				Vector<String> columnData = new Vector<String>();

				for (i = 1; i <= q; i++) {
					columnData.add(rs.getString("musicid"));
					columnData.add(rs.getString("musicname"));
					columnData.add(rs.getString("musiclang"));
					columnData.add(rs.getString("musiccategory"));
					columnData.add(rs.getString("musicsinger"));
					columnData.add(rs.getString("musicpath"));
				}
				RecordTable.addRow(columnData);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}

	// ========================刷新函数==========================

	/**
	 * 主方法单独运行这个界面
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Music_MysqlConn window = new Music_MysqlConn();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public Music_MysqlConn() {
		initialize();
	}

	/**
	 * 初始化
	 */
	private void initialize() {
		// 设置窗体
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null); // 中心
		frame.setUndecorated(false); //
		frame.setResizable(false);// 大小不能该

		// 设置主面板
		panelMain = new JPanel();
		panelMain.setBorder(new LineBorder(new Color(20, 40, 80), 8));
		panelMain.setBounds(0, 0, 650, 572);
		frame.getContentPane().add(panelMain);
		panelMain.setLayout(null);

		// 设置增删改查按钮的面板
		panelButtons = new JPanel();
		panelButtons.setBackground(new Color(218, 225, 231));
		panelButtons.setLayout(null);
		panelButtons.setBorder(new LineBorder(new Color(20, 40, 80), 8));
		panelButtons.setBounds(446, 82, 198, 484);
		panelMain.add(panelButtons);

		// 标题个Logo图片
		panelHeader = new JPanel();
		panelHeader.setBackground(new Color(218, 225, 231));
		panelHeader.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelHeader.setBounds(17, 23, 616, 47);
		panelMain.add(panelHeader);
		panelHeader.setLayout(null);

		lblTitle = new JLabel("   歌曲增删改查");
		lblTitle.setVerticalTextPosition(SwingConstants.BOTTOM);
		lblTitle.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTitle.setIconTextGap(3);
//		lblTitle.setIcon(new ImageIcon("/Users/zhaki/eclips_workspace/0MusicManagementSystem/AudioIcons/music.png"));
		lblTitle.setIcon(new ImageIcon(getClass().getClassLoader().getResource("music.png")));
		lblTitle.setForeground(new Color(30, 144, 255));
		lblTitle.setBounds(107, 6, 399, 35);
		panelHeader.add(lblTitle);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("DIN Condensed", Font.BOLD, 21));

		// 设置JTable表格的面板
		panelTable = new JPanel();
		panelTable.setBackground(new Color(218, 225, 231));
		panelTable.setBorder(new LineBorder(new Color(20, 40, 80), 8));
		panelTable.setBounds(6, 82, 440, 484);
		panelMain.add(panelTable);
		panelTable.setLayout(null);

		/**
		 * 设置给用户显示的标签以及文本框
		 */
		lblid_music = new JLabel("编号");
		lblid_music.setForeground(new Color(30, 144, 255));
		lblid_music.setFont(new Font("Raanana", Font.BOLD, 13));
		lblid_music.setBounds(24, 17, 116, 16);
		panelTable.add(lblid_music);

		textID = new JTextField();
		textID.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		textID.setColumns(10);
		textID.setBounds(141, 13, 229, 26);
		panelTable.add(textID);

		lblname_music = new JLabel("歌名");
		lblname_music.setForeground(new Color(30, 144, 255));
		lblname_music.setBounds(25, 41, 116, 16);
		lblname_music.setFont(new Font("Raanana", Font.BOLD, 13));
		panelTable.add(lblname_music);

		textNameMusic = new JTextField();
		textNameMusic.setBounds(140, 37, 230, 26);
		textNameMusic.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		textNameMusic.setColumns(10);
		panelTable.add(textNameMusic);

		lblLanguage_music_1 = new JLabel("语言");
		lblLanguage_music_1.setForeground(new Color(30, 144, 255));
		lblLanguage_music_1.setFont(new Font("Raanana", Font.BOLD, 13));
		lblLanguage_music_1.setBounds(25, 69, 116, 16);
		panelTable.add(lblLanguage_music_1);

		textLang = new JTextField();
		textLang.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		textLang.setColumns(10);
		textLang.setBounds(140, 65, 230, 26);
		panelTable.add(textLang);

		lblCategory_music = new JLabel("类别");
		lblCategory_music.setForeground(new Color(30, 144, 255));
		lblCategory_music.setBounds(25, 97, 116, 16);
		lblCategory_music.setFont(new Font("Raanana", Font.BOLD, 13));
		panelTable.add(lblCategory_music);

		textCategory = new JTextField();
		textCategory.setBounds(140, 93, 230, 26);
		textCategory.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		textCategory.setColumns(10);
		panelTable.add(textCategory);

		lblSinger_music = new JLabel("歌手");
		lblSinger_music.setForeground(new Color(30, 144, 255));
		lblSinger_music.setFont(new Font("Raanana", Font.BOLD, 13));
		lblSinger_music.setBounds(25, 125, 116, 16);
		panelTable.add(lblSinger_music);

		textSinger = new JTextField();
		textSinger.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		textSinger.setColumns(10);
		textSinger.setBounds(140, 121, 230, 26);
		panelTable.add(textSinger);

		lblfilePath_music = new JLabel("文件路径");
		lblfilePath_music.setForeground(new Color(30, 144, 255));
		lblfilePath_music.setBounds(25, 153, 116, 16);
		lblfilePath_music.setFont(new Font("Raanana", Font.BOLD, 13));
		panelTable.add(lblfilePath_music);

		textSongNamePath = new JTextField();
		textSongNamePath.setBounds(140, 149, 230, 26);
		textSongNamePath.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		panelTable.add(textSongNamePath);
		textSongNamePath.setColumns(10);

		// 增加功能
		btnAddNew = new JButton("增加");
		btnAddNew.setForeground(new Color(30, 144, 255));
		btnAddNew.setFont(new Font("Raanana", Font.BOLD, 14));
//		btnAddNew.setIcon(new ImageIcon("AudioIcons/inseart.png"));
		btnAddNew.setIcon(new ImageIcon(getClass().getClassLoader().getResource("inseart.png")));
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.print("文件路径 - " + path);
				System.out.print("歌名 " + f.getName());
				File f = new File(path);
				try {
					InputStream is = new FileInputStream(f);
//					Class.forName("com.mysql.jdbc.Driver"); // 启动我的驱动程序]
					sqlConn = DriverManager.getConnection(dataConn, username, password);
					pst = sqlConn.prepareStatement(
							"insert into music_table(musicname, musiclang, musiccategory, musicsinger, musicpath)value(?,?,?,?,?)");
					pst.setString(1, f.getName());
					pst.setString(2, textLang.getText());
					pst.setString(3, textCategory.getText());
					pst.setString(4, textSinger.getText());
					pst.setString(5, path);

					int inserted = pst.executeUpdate();

					if (inserted > 0) {
						JOptionPane.showMessageDialog(btnAddNew, "增加成功");
						upDate(); // 加载ID
					}
//					JOptionPane.showMessageDialog(jbtnAddNew, this, "增加成功", deleteItem, null);

				} catch (SQLException ex) {
					java.util.logging.Logger.getLogger(Music_MysqlConn.class.getName())
							.log(java.util.logging.Level.SEVERE, null, ex);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAddNew.setBounds(45, 190, 117, 37);
		panelButtons.add(btnAddNew);

		// 修改功能
		btnUpdate = new JButton("修改");
		btnUpdate.setForeground(new Color(30, 144, 255));
		btnUpdate.setFont(new Font("Raanana", Font.BOLD, 14));
//		btnUpdate.setIcon(new ImageIcon("AudioIcons/update.png"));
		btnUpdate.setIcon(new ImageIcon(getClass().getClassLoader().getResource("update.png")));
		btnUpdate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				DefaultTableModel RecordTable = (DefaultTableModel) table.getModel();
				int SelectedRows = table.getSelectedRow();
				if (e.getSource() == btnUpdate) {
					int rowIndex = table.getSelectedRow();
					if (rowIndex < 0) {
//						  JOptionPane.showInputDialog(this, "未选择待删除的记录!");
						JOptionPane.showMessageDialog(btnUpdate, "未选择待删除的记录!", null, rowIndex);
					}
				}
				try {
					numUpdate = Integer.parseInt(RecordTable.getValueAt(SelectedRows, 0).toString());
					pst = sqlConn.prepareStatement(
							"update music_table set musicname =?, musiclang = ?, musiccategory = ?, musicsinger = ?, musicpath = ? where musicid = ?");
					Class.forName("com.mysql.jdbc.Driver");
					sqlConn = DriverManager.getConnection(dataConn, username, password);

//					pst.setInt(1, textField.getText());
					pst.setString(6, textID.getText());
					pst.setString(1, textNameMusic.getName());
					pst.setString(2, textLang.getText());
					pst.setString(3, textCategory.getText());
					pst.setString(4, textSinger.getText());
					pst.setString(5, textSongNamePath.getText());

					update = JOptionPane.showConfirmDialog(null, "是否要修改编号为" + numUpdate + "歌曲记录?", "Warning",
							JOptionPane.YES_NO_OPTION);
					if (e.getSource() == btnUpdate || update == JOptionPane.YES_OPTION) {
						JOptionPane.showMessageDialog(btnUpdate, "编号为" + numUpdate + "歌曲修改成功");
						pst.executeUpdate();
						upDate();
					} else {
						JOptionPane.showMessageDialog(btnUpdate, "编号为" + numUpdate + "歌曲未修改成功");
					}
				} catch (ClassNotFoundException ex) {
					java.util.logging.Logger.getLogger(Music_MysqlConn.class.getName())
							.log(java.util.logging.Level.SEVERE, null, ex);

				} catch (SQLException ex) {
					System.err.println(ex);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		});

		btnUpdate.setBounds(45, 239, 117, 37);
		panelButtons.add(btnUpdate);

		// 重置
		btnRest = new JButton("重置");
		btnRest.setForeground(new Color(30, 144, 255));
		btnRest.setFont(new Font("Raanana", Font.BOLD, 14));
//		btnRest.setIcon(new ImageIcon("AudioIcons/settings.png"));
		btnRest.setIcon(new ImageIcon(getClass().getClassLoader().getResource("settings.png")));
		btnRest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textID.setText("");
				textNameMusic.setText("");
				textLang.setText("");
				textCategory.setText("");
				textSinger.setText("");
				textSongNamePath.setText("");
				textSerch.setText("");

			}
		});
		btnRest.setBounds(45, 288, 117, 37);
		panelButtons.add(btnRest);

		// 删除功能
		btnDelete = new JButton("删除");
		btnDelete.setForeground(new Color(30, 144, 255));
		btnDelete.setFont(new Font("Raanana", Font.BOLD, 14));
//		btnDelete.setIcon(new ImageIcon("AudioIcons/send-to-trash.png"));
		btnDelete.setIcon(new ImageIcon(getClass().getClassLoader().getResource("send-to-trash.png")));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel RecordTable = (DefaultTableModel) table.getModel();
				int SelectedRows = table.getSelectedRow();
				if (e.getSource() == btnDelete) {
					int rowIndex = table.getSelectedRow();
					if (rowIndex < 0) {
//						  JOptionPane.showInputDialog(this, "未选择待删除的记录!");
						JOptionPane.showMessageDialog(btnDelete, "未选择待删除的记录!", path, rowIndex);
					}
				}
				try {
					num = Integer.parseInt(RecordTable.getValueAt(SelectedRows, 0).toString());

					deleteItem = JOptionPane.showConfirmDialog(null, "确认要删除编号为" + num + "歌曲记录?", "Warning",
							JOptionPane.YES_NO_OPTION);
					if (deleteItem == JOptionPane.YES_OPTION) {
						Class.forName("com.mysql.jdbc.Driver");
						sqlConn = DriverManager.getConnection(dataConn, username, password);
						pst = sqlConn.prepareStatement("delete from music_table where musicid =?");

						pst.setInt(1, num);
						pst.executeUpdate();

						textNameMusic.setText("");
						textLang.setText("");
						textCategory.setText("");
						textSinger.setText("");
						textSongNamePath.setText("");
						sqlConn.close();
						JOptionPane.showMessageDialog(btnDelete, "编号为" + num + "删除成功");
//						JOptionPane.showMessageDialog(jbtnDelete, this, "号为"+num+"删除成功", SelectedRows);
						upDate();

					}
				} catch (ArrayIndexOutOfBoundsException e1) {
					java.util.logging.Logger.getLogger(Music_MysqlConn.class.getName())
							.log(java.util.logging.Level.SEVERE, null, e1);
				} catch (ClassNotFoundException ex) {
					java.util.logging.Logger.getLogger(Music_MysqlConn.class.getName())
							.log(java.util.logging.Level.SEVERE, null, ex);

				} catch (SQLException ex) {
					System.err.println(ex);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			private String String(String string) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		btnDelete.setBounds(45, 337, 117, 37);
		panelButtons.add(btnDelete);

		/*
		 * 返回到个人音乐播放器界面
		 */
		btnExit = new JButton("音乐");
		btnExit.setForeground(new Color(30, 144, 255));
		btnExit.setFont(new Font("Raanana", Font.BOLD, 14));
//		btnExit.setIcon(new ImageIcon("AudioIcons/folder (7).png"));
		btnExit.setIcon(new ImageIcon(getClass().getClassLoader().getResource("folder (7).png")));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				
				dispose();
				MusicPlayerFrame musicPlayerFrame = new MusicPlayerFrame();
				musicPlayerFrame.frame.setVisible(true);

			}
		});
		// +================================================================

		// ================
		btnExit.setBounds(45, 386, 117, 37);
		panelButtons.add(btnExit);

		/*
		 * 从数据库读出信息
		 */
		btnInfo = new JButton("歌曲");
		btnInfo.setForeground(new Color(30, 144, 255));
		btnInfo.setFont(new Font("Raanana", Font.BOLD, 14));
//		btnInfo.setIcon(new ImageIcon("AudioIcons/info.png"));
		btnInfo.setIcon(new ImageIcon(getClass().getClassLoader().getResource("info.png")));
		btnInfo.setActionCommand("");
		btnInfo.addActionListener(new ActionListener() {
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
		btnInfo.setBounds(45, 92, 117, 37);
		panelButtons.add(btnInfo);

		btnUpload = new JButton("上传");
		btnUpload.setForeground(new Color(30, 144, 255));
		btnUpload.setFont(new Font("Raanana", Font.BOLD, 14));
//		btnUpload.setIcon(new ImageIcon("AudioIcons/upload_2.png"));
		btnUpload.setIcon(new ImageIcon(getClass().getClassLoader().getResource("upload_2.png")));
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter fnwf = new FileNameExtensionFilter("只选择.MP3", "mp3");

				// 文件选择器
				fileChooser.addChoosableFileFilter(fnwf);
				int load = fileChooser.showOpenDialog(null);

				// 因此如果单击打开按钮
				if (load == fileChooser.APPROVE_OPTION) { // 文件选择器批准选项
					// 调用文件选择器
					f = fileChooser.getSelectedFile();

					path = f.getAbsolutePath();
					textSongNamePath.setText(path); // JTextField

				}
			}
		});
		btnUpload.setBounds(45, 141, 117, 37);
		panelButtons.add(btnUpload);

		lbliconinbtn = new JLabel("");
		lbliconinbtn.setHorizontalAlignment(SwingConstants.CENTER);
//		lbliconinbtn.setIcon(new ImageIcon("AudioIcons/appGif.gif"));
		lbliconinbtn.setIcon(new ImageIcon(getClass().getClassLoader().getResource("appGif.gif")));
		lbliconinbtn.setBounds(45, 22, 117, 60);
		panelButtons.add(lbliconinbtn);

		lblSearch_music = new JLabel("歌名为关键字");
		lblSearch_music.setForeground(new Color(30, 144, 255));
		lblSearch_music.setFont(new Font("Raanana", Font.BOLD, 13));
		lblSearch_music.setBounds(25, 181, 116, 16);
		panelTable.add(lblSearch_music);

		textSerch = new JTextField();
		textSerch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				ResultSet rs = null;

				try {

					pst = sqlConn.prepareStatement("select * from music_table where musicid = ?");
					Class.forName("com.mysql.jdbc.Driver");
					sqlConn = DriverManager.getConnection(dataConn, username, password);

					pst.setString(1, textSerch.getText());
					rs = pst.executeQuery();
					if (rs.next()) {
						textID.setText(rs.getString("musicid"));
						String value1 = rs.getString("musicname");
						textNameMusic.setText(value1);
						textLang.setText(rs.getString("musiclang"));
						textCategory.setText(rs.getString("musiccategory"));
						textSinger.setText(rs.getString("musicsinger"));
						textSongNamePath.setText(rs.getString("musicpath"));

					}

				} catch (Exception e1) {

				}
			}
		});
		textSerch.setBounds(140, 176, 164, 26);
		panelTable.add(textSerch);
		textSerch.setColumns(10);

		// 查找
		btnSearch = new JButton("查找");
		btnSearch.setFont(new Font("Raanana", Font.BOLD, 13));
		btnSearch.setForeground(new Color(30, 144, 255));

		btnSearch.setBounds(306, 176, 117, 29);
		panelTable.add(btnSearch);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel RecordTable = (DefaultTableModel) table.getModel();
				int SelectedRows = table.getSelectedRow();

				textNameMusic.setText(RecordTable.getValueAt(SelectedRows, 1).toString());
				textLang.setText(RecordTable.getValueAt(SelectedRows, 1).toString());
				textCategory.setText(RecordTable.getValueAt(SelectedRows, 1).toString());
				textSinger.setText(RecordTable.getValueAt(SelectedRows, 1).toString());
				textSongNamePath.setText(RecordTable.getValueAt(SelectedRows, 1).toString());

			}
		});
		scrollPane_1.setBounds(25, 209, 398, 256);
		panelTable.add(scrollPane_1);

		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		table.setForeground(Color.BLACK);
		table.setBounds(24, 254, 328, 90);
		table.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "编号", "歌名", "语言", "类别", "歌手", "文件路径" }));

		scrollPane_1.setViewportView(table);

		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Statement stmt = null;
				ResultSet rs = null;

				try {

					pst = sqlConn.prepareStatement("select * from music_table where musicname = ?");
					Class.forName("com.mysql.jdbc.Driver");
					sqlConn = DriverManager.getConnection(dataConn, username, password);

					pst.setString(1, textSerch.getText());
					rs = pst.executeQuery();
					if (rs.next()) {
						textID.setText(rs.getString("musicid"));
						String value1 = rs.getString("musicname");
						textNameMusic.setText(value1);
						textLang.setText(rs.getString("musiclang"));
						textCategory.setText(rs.getString("musiccategory"));
						textSinger.setText(rs.getString("musicsinger"));
						textSongNamePath.setText(rs.getString("musicpath"));

					}

				} catch (Exception e1) {

				}

			}

		});

	}

	protected void dispose() {
		// TODO Auto-generated method stub

	}
}
