
package dp_2020L038;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Panel;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
/*
 * 创建可执行的jar文件
 */
/***
 * 
 * @author zhakiDapeng
 * @version 1.0 Login 这项目的主界面, 用户从这个界面登录进去个人音乐播放器系统 数据库为demo, 数据表为users
 *          https://music.163.com/
 */
public class Login {
	/**
	 * Frame窗体以及它的主面板
	 */
	public JFrame frame;
	private JPanel panelMain;

	/**
	 * 我的登录界面在子面板上设置的
	 */
	private JPanel panelSubLogin;

	// 给用户显示的标签以及输入的文本框
	private JLabel lblUser;
	private JTextField textUser;

	private JLabel lblPassword;
	private JPasswordField passwordText;
	private JButton btnChangePassword;

	// 组合框: 普通用户, 管理员
	private JComboBox<Object> comboBox;

	/*
	 * 登录, 注册按钮
	 */
	private JButton btnLogin;
	private JButton btnRegister;

	/**
	 * 在登录界面的子面板上再设置一个子面板, JLabel设置我的Logo
	 */
	private JPanel panelLogo;
	private JLabel lblMusicLogo;

	// GIF动态的图片设置
	private JPanel panelSubImg;
	private JLabel lblbackphoto;

	/**
	 * 主方法来启动程序
	 */
	public static void main(String[] args) {
//		dispose();
		Login login = new Login();
		login.frame.setVisible(true);
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					dispose();
//					Login window = new Login();
//					window.frame.setLocationRelativeTo(null);
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}

	/**
	 * 调用我的数据库连接的类
	 */
	JDBC_Connection conn; // 数据库连接

	/**
	 * 构造函数
	 */
	public Login() {
		initialize();
	}

	/**
	 * 初始化框架的内容
	 */
	private void initialize() {
		// 设置窗体
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 540);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null); // 中心
		frame.setUndecorated(false); // 可以把默认的关闭功能拿掉X
		frame.setResizable(false);// 大小不能改

		// 设置主面板
		panelMain = new JPanel();
		panelMain.setBackground(new Color(20, 40, 80));
		panelMain.setBounds(0, 0, 650, 510);
		frame.getContentPane().add(panelMain);
		panelMain.setLayout(null);

		// 设置登录,注册子面板
		panelSubLogin = new JPanel();
		panelSubLogin.setBorder(new LineBorder(new Color(218, 225, 231), 3));
		panelSubLogin.setBackground(new Color(20, 40, 80));
		panelSubLogin.setBounds(318, 0, 332, 510);
		panelMain.add(panelSubLogin);
		panelSubLogin.setLayout(null);

		// 设置用户的文本框以及显示文字标签
		lblUser = new JLabel("  用户");
		lblUser.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUser.setFont(new Font("Raanana", Font.BOLD, 17));
//		lblUser.setIcon(new ImageIcon("img/user.png"));
		lblUser.setIcon(new ImageIcon(getClass().getClassLoader().getResource("user.png")));
		lblUser.setForeground(new Color(71, 181, 255));
		lblUser.setBounds(28, 135, 106, 43);
		panelSubLogin.add(lblUser);

		textUser = new JTextField();
		textUser.setFont(new Font("Raanana", Font.BOLD, 13));
		textUser.setBackground(new Color(218, 225, 231));
		textUser.setBounds(146, 134, 154, 43);
		panelSubLogin.add(textUser);
		textUser.setColumns(10);

		// 设置密码的文本框以及显示文字
		lblPassword = new JLabel("  密码");
		lblPassword.setHorizontalAlignment(SwingConstants.TRAILING);
//		lblPassword.setIcon(new ImageIcon("img/password2.png"));
		lblPassword.setIcon(new ImageIcon(getClass().getClassLoader().getResource("password2.png")));
		lblPassword.setForeground(new Color(71, 181, 255));
		lblPassword.setFont(new Font("Rockwell", Font.BOLD, 17));
		lblPassword.setBounds(28, 195, 106, 43);
		panelSubLogin.add(lblPassword);

		passwordText = new JPasswordField();
		passwordText.setFont(new Font("Raanana", Font.BOLD, 13));
		passwordText.setBackground(new Color(218, 225, 231));
		passwordText.setBounds(146, 192, 154, 43);
		panelSubLogin.add(passwordText);

		/*
		 * 打开修改密码界面
		 */
		btnChangePassword = new JButton("修改密码");
		btnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnChangePassword) {
					dispose();
					ChangePassword changePassword = new ChangePassword();
					changePassword.frame.setVisible(true);
				}
			}
		});
		btnChangePassword.setForeground(new Color(0, 191, 255));
		btnChangePassword.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		btnChangePassword.setBounds(156, 247, 66, 22);
		panelSubLogin.add(btnChangePassword);

		// 设置组合框
		String[] user = { "管理员", "普通用户" };
		comboBox = new JComboBox<Object>(user);
		comboBox.setForeground(new Color(0, 191, 255));
		comboBox.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		comboBox.setBackground(new Color(218, 225, 231));
		comboBox.setBounds(146, 279, 154, 43);
		panelSubLogin.add(comboBox);

		// 用户输入信息时判断可能不输入密码, 直接点击登录按钮, 需要给用户提示
		btnLogin = new JButton("登录");
		btnLogin.setBackground(new Color(218, 225, 231));
		btnLogin.setForeground(new Color(71, 181, 255));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textUser.getText();
				String password = String.valueOf(passwordText.getPassword());
				if (username.isEmpty()) {
					JOptionPane.showMessageDialog(btnLogin, "请输入用户名", "错误", JOptionPane.ERROR_MESSAGE);
				} else if (password.isEmpty()) {
					JOptionPane.showMessageDialog(btnLogin, "请输入密码", "错误", JOptionPane.ERROR_MESSAGE);
				} else {
					// 开始登录过程。
					userLogin(username, password);
				}
			}

			/*
			 * 连接完数据库, 开始读取, 核实用户输入的信息
			 */
			private void userLogin(String username, String password) {
				Connection dbconn = JDBC_Connection.connectJDBS();
				if (dbconn != null) {
					try {
						PreparedStatement st = (PreparedStatement) dbconn
								.prepareStatement("Select * from users WHERE username = ? AND password = ?");

						st.setString(1, username);
						st.setString(2, password);
						/*
						 * 执行这个PreparedStatement对象中的SQL查询，并返回查询生成的 并返回由查询生成的 ResultSet 对象。
						 */
						ResultSet res = st.executeQuery(); // 执行数据库查询
						if (res.next()) {
							/*
							 * 登录后显示音乐播放器页面。
							 */
							EventQueue.invokeLater(new Runnable() {
								public void run() {
									try {
										MusicPlayerFrame window = new MusicPlayerFrame();
										window.frame.setTitle("个人音乐管理界面");
										window.frame.setVisible(true);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							});
						} else {
							System.out.println("username " + username);
							System.out.println("password " + password);
							JOptionPane.showMessageDialog(btnLogin, "用户名或密码错误, 请重新输入", "错误", JOptionPane.ERROR_MESSAGE);
						}
					} catch (SQLException e) {
						Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
						e.printStackTrace();
					}
				} else {
					System.out.println("数据库连接无效");
				}
			}

			private void dispose() {
			}
		});
		btnLogin.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnLogin.setBounds(28, 325, 106, 43);
		panelSubLogin.add(btnLogin);

		// 如果用户不在数据库里需要注册新的账户
		// 在这打开修改密码界面
		btnRegister = new JButton("注册");
		btnRegister.setBackground(new Color(218, 225, 231));
		btnRegister.setForeground(new Color(71, 181, 255));
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Register register = new Register();
				register.frame.setTitle("注册界面");
				register.frame.setVisible(true);

			}
		});
		btnRegister.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnRegister.setBounds(194, 325, 106, 43);
		panelSubLogin.add(btnRegister);

		// 设置我的Logo子面板, 把它添加到主面板下的子面板
		panelLogo = new JPanel();
		panelLogo.setBackground(new Color(218, 225, 231));
		panelLogo.setBounds(6, 6, 320, 70);
		panelSubLogin.add(panelLogo);
		panelLogo.setLayout(null);

		// 设置Logo图片
		lblMusicLogo = new JLabel("  音乐播放器");
		lblMusicLogo.setForeground(new Color(71, 181, 255));
//		lblMusicLogo.setIcon(new ImageIcon("img/logo.png"));
		lblMusicLogo.setIcon(new ImageIcon(getClass().getClassLoader().getResource("logo.png")));
		lblMusicLogo.setFont(new Font("Raanana", Font.BOLD | Font.ITALIC, 27));
		lblMusicLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblMusicLogo.setBounds(6, 6, 308, 58);
		panelLogo.add(lblMusicLogo);

		// GIF动态的图片设置
		panelSubImg = new JPanel();
		panelSubImg.setBorder(new LineBorder(new Color(0, 0, 205)));
		panelSubImg.setBounds(6, 6, 307, 498);
		panelMain.add(panelSubImg);
		panelSubImg.setLayout(null);

		lblbackphoto = new JLabel("");
		lblbackphoto.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblbackphoto.setBounds(6, 6, 295, 486);
		panelSubImg.add(lblbackphoto);
		lblbackphoto.setForeground(new Color(135, 206, 235));
		lblbackphoto.setFont(new Font("Lucida Grande", Font.BOLD, 14));
//		lblbackphoto.setIcon(new ImageIcon("img/loginlogo.gif"));
		lblbackphoto.setIcon(new ImageIcon(getClass().getClassLoader().getResource("loginlogo.gif")));

	}

	protected void dispose() {
		// TODO Auto-generated method stub

	}

}
