package dp_2020L038;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;

/***
 * 
 * @author zhakiDapeng
 * @version 1.0 Register类, 在数据库上用户名没有的话, 用户在这这注册
 */
public class Register {

	// 窗体
	public JFrame frame;
	private JPanel panelMain; // 主面板
	private JPanel panelSub; // 子面板

	/**
	 * 设置给用户显示的标签以及用户输入的文本框
	 */
	private JLabel lblName;
	private JTextField textName;
	private JLabel lblLastName;
	private JTextField textLastName;
	private JLabel lblUser;
	private JTextField textUser;
	private JLabel lblPassword;
	private JPasswordField passwordText;

	/*
	 * 两个按钮: 返回登录界面以及注册界面
	 */
	private JButton btnLogin;
	private JButton btnRegister;

	/*
	 * 界面的Logo
	 */
	private JPanel panelOfLogo;
	private JLabel lblMusicLogo;

	/*
	 * 在界面中设置图片
	 */
	private JPanel panelImage;
	private JLabel lblimg;

	/**
	 * 也可以单独运行只这类, 看效果
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Register window = new Register();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/*
	 * 调用initialize();方法
	 */
	public Register() {
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
		frame.setUndecorated(false); //
		frame.setResizable(false);// 大小不能该

		// 设置主面板
		panelMain = new JPanel();
		panelMain.setLayout(null);
		panelMain.setBackground(new Color(20, 40, 80));
		panelMain.setBounds(0, 0, 650, 510);
		frame.getContentPane().add(panelMain);

		// 设置子面板
		panelSub = new JPanel();
		panelSub.setLayout(null);
		panelSub.setBorder(new LineBorder(new Color(218, 225, 231), 3));
		panelSub.setBackground(new Color(20, 40, 80));
		panelSub.setBounds(0, 0, 650, 510);
		panelMain.add(panelSub);

		/*
		 * 名字的文本框
		 */
		lblName = new JLabel("  名字");
		lblName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblName.setForeground(new Color(71, 181, 255));
		lblName.setFont(new Font("Raanana", Font.BOLD, 17));
		lblName.setBounds(6, 135, 106, 43);
		panelSub.add(lblName);
		textName = new JTextField();
		textName.setFont(new Font("Raanana", Font.BOLD, 13));
		textName.setColumns(10);
		textName.setBackground(new Color(218, 225, 231));
		textName.setBounds(124, 134, 154, 43);
		panelSub.add(textName);

		/*
		 * 姓的文本框
		 */
		lblLastName = new JLabel("  姓");
		lblLastName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLastName.setForeground(new Color(71, 181, 255));
		lblLastName.setFont(new Font("Rockwell", Font.BOLD, 17));
		lblLastName.setBounds(6, 195, 106, 43);
		panelSub.add(lblLastName);
		textLastName = new JTextField();
		textLastName.setFont(new Font("Raanana", Font.BOLD, 13));
		textLastName.setColumns(10);
		textLastName.setBackground(new Color(218, 225, 231));
		textLastName.setBounds(124, 189, 154, 43);
		panelSub.add(textLastName);

		/*
		 * 用户名文本框
		 */
		lblUser = new JLabel("  用户");
		lblUser.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUser.setForeground(new Color(71, 181, 255));
		lblUser.setFont(new Font("Rockwell", Font.BOLD, 17));
		lblUser.setBounds(6, 250, 106, 43);
		panelSub.add(lblUser);
		textUser = new JTextField();
		textUser.setFont(new Font("Raanana", Font.BOLD, 13));
		textUser.setColumns(10);
		textUser.setBackground(new Color(218, 225, 231));
		textUser.setBounds(124, 244, 154, 43);
		panelSub.add(textUser);

		/*
		 * 设置密码
		 */
		lblPassword = new JLabel("  密码");
		lblPassword.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPassword.setForeground(new Color(71, 181, 255));
		lblPassword.setFont(new Font("Rockwell", Font.BOLD, 17));
		lblPassword.setBounds(6, 299, 106, 43);
		panelSub.add(lblPassword);
		passwordText = new JPasswordField();
		passwordText.setFont(new Font("Raanana", Font.BOLD, 13));
		passwordText.setBackground(new Color(218, 225, 231));
		passwordText.setBounds(124, 299, 154, 43);
		panelSub.add(passwordText);

		/*
		 * 两个按钮: 返回登录界面以及注册界面
		 */
		/**
		 * 注册成功之后回去登录页面
		 */

		btnLogin = new JButton("返回登录");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Login backLogin = new Login();
				backLogin.frame.setLocationRelativeTo(null);
				backLogin.frame.setVisible(true);
			}

			private void dispose() {
				// TODO Auto-generated method stub

			}
		});
		btnLogin.setForeground(new Color(71, 181, 255));
		btnLogin.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnLogin.setBackground(new Color(218, 225, 231));
		btnLogin.setBounds(16, 354, 106, 43);
		panelSub.add(btnLogin);

		// 注册
		/**
		 * 设置注册按钮与数据库连接
		 */
		btnRegister = new JButton("注册");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fname = textName.getText();
				String fLastname = textLastName.getText();

				String username = textUser.getText();
				String password = String.valueOf(passwordText.getPassword());
//				String password = passwordText.getText();
				if (fname.isEmpty()) {
					JOptionPane.showMessageDialog(btnLogin, "请输入名字", "错误", JOptionPane.ERROR_MESSAGE);
				} else if (fLastname.isEmpty()) {
					JOptionPane.showMessageDialog(btnLogin, "请输入姓", "错误", JOptionPane.ERROR_MESSAGE);
				}
				if (username.isEmpty()) {
					JOptionPane.showMessageDialog(btnLogin, "请输入用户名", "错误", JOptionPane.ERROR_MESSAGE);
				} else if (password.isEmpty()) {
					JOptionPane.showMessageDialog(btnLogin, "请输入用密码", "错误", JOptionPane.ERROR_MESSAGE);
				} else {
					// 开始登录过程
//					userLogin(username, password);
					userRegister(fname, fLastname, username, password);
				}
			}

			private void userRegister(String fname, String fLastname, String username, String password) {
				// TODO 连接数据库
				Connection dbconn = JDBC_Connection.connectJDBS();
				if (dbconn != null) {
					try {
						PreparedStatement st = (PreparedStatement) dbconn.prepareStatement(
								"INSERT INTO users(firstname, lastname, username, password) VALUE(?,?,?,?)");

						st.setString(1, fname);
						st.setString(2, fLastname);
						st.setString(3, username);
						st.setString(4, password);
						/*
						 * 执行这个PreparedStatement对象中的SQL查询，并返回查询生成的 由查询生成的ResultSet对象。
						 */
						int res = st.executeUpdate();
						JOptionPane.showMessageDialog(btnRegister, "注册成功!", "成功", JOptionPane.INFORMATION_MESSAGE);

						textName.setText("");
						textLastName.setText("");
						textUser.setText("");
						passwordText.setText("");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
						e.printStackTrace();
					}
				} else {
					System.out.println("数据库连接无效");
				}
			}
		});
		btnRegister.setForeground(new Color(71, 181, 255));
		btnRegister.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnRegister.setBackground(new Color(218, 225, 231));
		btnRegister.setBounds(172, 354, 106, 43);
		panelSub.add(btnRegister);

		/*
		 * 界面的Logo
		 */

		panelOfLogo = new JPanel();
		panelOfLogo.setLayout(null);
		panelOfLogo.setBackground(new Color(218, 225, 231));
		panelOfLogo.setBounds(6, 6, 638, 70);
		panelSub.add(panelOfLogo);
		lblMusicLogo = new JLabel("    音乐播放器注册界面");
		lblMusicLogo.setBounds(6, 6, 626, 58);

//		lblMusicLogo.setIcon(new ImageIcon("img/logo.png"));
		lblMusicLogo.setIcon(new ImageIcon(getClass().getClassLoader().getResource("logo.png")));
		lblMusicLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblMusicLogo.setForeground(new Color(71, 181, 255));
		lblMusicLogo.setFont(new Font("Raanana", Font.BOLD | Font.ITALIC, 27));
		panelOfLogo.add(lblMusicLogo);

		/**
		 * 设置注册界面有边大的图片
		 */
		panelImage = new JPanel();
		panelImage.setBorder(new LineBorder(new Color(135, 206, 235), 1, true));
		panelImage.setBounds(308, 79, 336, 425);
		panelSub.add(panelImage);
		panelImage.setLayout(null);

		lblimg = new JLabel("");
		lblimg.setBounds(6, 6, 324, 413);
		panelImage.add(lblimg);
//		lblimg.setIcon(new ImageIcon("img/registericon.png"));
		lblimg.setIcon(new ImageIcon(getClass().getClassLoader().getResource("registericon.png")));
	}

}
