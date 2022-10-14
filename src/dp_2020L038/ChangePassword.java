package dp_2020L038;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

/***
 * 
 * @author zhakiDapeng
 * @version 1.0 ChangePassword 修改密码界面
 */
public class ChangePassword {

	public JFrame frame; // 窗体
	private JPanel panelMain; // 主面板

	private JPanel panelSubMain; // 子面板

	/*
	 * 设置我的Logo和标题
	 */
	private JPanel panelHeader;
	private JLabel lblMusicLogo;

	/*
	 * 两个按钮: 修改密码, 返回到登录界面去登录
	 */
	private JButton btnLogin;
	private JButton btnChangePass;

	/*
	 * 给用户显示标签, “用户”, 旧密码, 新密码, 再次确认密码
	 */
	private JLabel lblUser;
	private JLabel lblOldPass;
	private JLabel lblNewPass;
	private JLabel lblPasswordChange;

	// 用户输入的文本框
	private JTextField textUser;
	private JPasswordField passwordFieldOld;
	private JPasswordField passwordFieldNew;
	private JPasswordField passwordFieldConfirm;

	// 设置大的图片
	private JPanel panelimgPass;
	private JLabel lblSetImg;

	// 设置数据库连接定常量
	private static final String username = "root";
	private static final String password = "dapeng";
	private static final String dataConn = "jdbc:mysql://localhost/demo";
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String id = null;
	Connection sqlConn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	int q, i, num, deleteItem;
	protected String update;
	String numUpdate;

	/**
	 * 把修改密码界面可以单独的运行看效果
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ChangePassword window = new ChangePassword();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	// ========================数据库刷新函数==========================
	public void upDate() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // 启动我的驱动程序]
			sqlConn = DriverManager.getConnection(dataConn, username, password);
			pst = sqlConn.prepareStatement("select * from users");

			// 结果
			rs = pst.executeQuery();
			ResultSetMetaData stData = rs.getMetaData();
			q = stData.getColumnCount(); // lie

			while (rs.next()) {
				Vector<String> columnData = new Vector<String>();

				for (i = 1; i <= q; i++) {

					columnData.add(rs.getString("username"));
					columnData.add(rs.getString("password"));

				}
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}

	// ========================Function==========================

	/**
	 * 调用initialize()
	 */
	public ChangePassword() {
		initialize();

	}

	/**
	 * 初始化框架的内容
	 */
	private void initialize() {
		// 窗体
		frame = new JFrame();
		frame.setBounds(100, 100, 651, 539);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("修改密码界面");
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

		// 子面板
		panelSubMain = new JPanel();
		panelSubMain.setLayout(null);
		panelSubMain.setBorder(new LineBorder(new Color(218, 225, 231), 3));
		panelSubMain.setBackground(new Color(20, 40, 80));
		panelSubMain.setBounds(0, 0, 650, 510);
		panelMain.add(panelSubMain);

		/*
		 * 设置我的Logo和标题
		 */
		panelHeader = new JPanel();
		panelHeader.setLayout(null);
		panelHeader.setBackground(new Color(218, 225, 231));
		panelHeader.setBounds(6, 6, 638, 70);
		panelSubMain.add(panelHeader);

		lblMusicLogo = new JLabel("    音乐播放器修改密码界面");
		lblMusicLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblMusicLogo.setForeground(new Color(71, 181, 255));
		lblMusicLogo.setFont(new Font("Raanana", Font.BOLD | Font.ITALIC, 27));
		lblMusicLogo.setBounds(6, 6, 626, 58);
		panelHeader.add(lblMusicLogo);

		/*
		 * 两个按钮: 修改密码, 返回到登录界面去登录
		 */
		btnLogin = new JButton("返回登录");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login backLogin = new Login();
				backLogin.frame.setLocationRelativeTo(null); // center the form
//				if (JOptionPane.showConfirmDialog(frame, "是否要退出程序", "MySQL Connector",
//						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
//					System.exit(1); // Exit
//				}
				backLogin.frame.setVisible(true);
			}
		});
		btnLogin.setForeground(new Color(71, 181, 255));
		btnLogin.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnLogin.setBackground(new Color(218, 225, 231));
		btnLogin.setBounds(16, 354, 106, 43);
		panelSubMain.add(btnLogin);

		btnChangePass = new JButton("修改");	
		btnChangePass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				String pst = passwordFieldConfirm.getText();
				try {
					Class.forName("com.mysql.cj.jdbc.Driver"); // 启动我的驱动程序]
					sqlConn = DriverManager.getConnection(dataConn, username, password);
					pst = sqlConn.prepareStatement("update users set password = ? where  username = ?");

					pst.setString(1, textUser.getText());
					pst.setString(2, passwordFieldConfirm.getText());

					pst.executeUpdate();

					JOptionPane.showMessageDialog(btnChangePass, "修改成功");
					upDate();

				} catch (ClassNotFoundException ex) {
					java.util.logging.Logger.getLogger(Music_MysqlConn.class.getName())
							.log(java.util.logging.Level.SEVERE, null, ex);

				} catch (SQLException ex) {
					System.err.println(ex);
				} catch (Exception ex) {
					java.util.logging.Logger.getLogger(ChangePassword.class.getName())
							.log(java.util.logging.Level.SEVERE, null, ex);
					ex.printStackTrace();
				}
			}
		});

		btnChangePass.setForeground(new Color(71, 181, 255));
		btnChangePass.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnChangePass.setBackground(new Color(218, 225, 231));
		btnChangePass.setBounds(172, 354, 106, 43);
		panelSubMain.add(btnChangePass);

		/*
		 * 给用户显示标签, “用户”, 旧密码, 新密码, 再次确认密码
		 */
		lblUser = new JLabel(" 用户");
		lblUser.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUser.setForeground(new Color(71, 181, 255));
		lblUser.setFont(new Font("Rockwell", Font.BOLD, 17));
		lblUser.setBounds(16, 135, 106, 43);
		panelSubMain.add(lblUser);

		textUser = new JTextField();
		textUser.setBounds(134, 129, 154, 43);
		panelSubMain.add(textUser);
		textUser.setColumns(10);

		lblOldPass = new JLabel("  旧密码");
		lblOldPass.setHorizontalAlignment(SwingConstants.TRAILING);
		lblOldPass.setForeground(new Color(71, 181, 255));
		lblOldPass.setFont(new Font("Rockwell", Font.BOLD, 17));
		lblOldPass.setBounds(16, 190, 106, 43);
		panelSubMain.add(lblOldPass);

		passwordFieldOld = new JPasswordField();
		passwordFieldOld.setFont(new Font("Raanana", Font.BOLD, 13));
		passwordFieldOld.setBackground(new Color(218, 225, 231));
		passwordFieldOld.setBounds(134, 184, 154, 43);
		panelSubMain.add(passwordFieldOld);

		lblNewPass = new JLabel("  新密码");
		lblNewPass.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewPass.setForeground(new Color(71, 181, 255));
		lblNewPass.setFont(new Font("Rockwell", Font.BOLD, 17));
		lblNewPass.setBounds(16, 239, 106, 43);
		panelSubMain.add(lblNewPass);

		passwordFieldNew = new JPasswordField();
		passwordFieldNew.setFont(new Font("Raanana", Font.BOLD, 13));
		passwordFieldNew.setBackground(new Color(218, 225, 231));
		passwordFieldNew.setBounds(134, 239, 154, 43);
		panelSubMain.add(passwordFieldNew);

		lblPasswordChange = new JLabel("再次确认密码");
		lblPasswordChange.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPasswordChange.setForeground(new Color(71, 181, 255));
		lblPasswordChange.setFont(new Font("Rockwell", Font.BOLD, 17));
		lblPasswordChange.setBounds(16, 294, 106, 43);
		panelSubMain.add(lblPasswordChange);

		passwordFieldConfirm = new JPasswordField();
		passwordFieldConfirm.setFont(new Font("Raanana", Font.BOLD, 13));
		passwordFieldConfirm.setBackground(new Color(218, 225, 231));
		passwordFieldConfirm.setBounds(134, 294, 154, 43);
		panelSubMain.add(passwordFieldConfirm);

		panelimgPass = new JPanel();
		panelimgPass.setLayout(null);
		panelimgPass.setBorder(new LineBorder(new Color(135, 206, 235), 1, true));
		panelimgPass.setBounds(308, 79, 336, 425);
		panelSubMain.add(panelimgPass);

		lblSetImg = new JLabel("");
//		lblSetImg.setIcon(new ImageIcon("AudioIcons/passLogo.png"));
		lblSetImg.setIcon(new ImageIcon(getClass().getClassLoader().getResource("passLogo.png")));
		lblSetImg.setBounds(6, 6, 324, 413);
		panelimgPass.add(lblSetImg);
	}
}
