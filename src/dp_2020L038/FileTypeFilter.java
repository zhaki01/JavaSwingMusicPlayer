package dp_2020L038;


import java.io.*;
import javax.swing.filechooser.FileFilter;
/**
 * 
 * @author zhakiDapeng
 * 带扩展名的文件名
 */
public class FileTypeFilter extends FileFilter{
	
	// 文件扩展字符串
	private final String extension;
	
	// 文件扩展名描述
	private final String description;
	
	// 构造方法
	public FileTypeFilter(String extension, String description) {
		// 设置构造器值
		this.extension = extension;
		this.description = description;
	}
	
	@Override
	public boolean accept(File file) {
		// 如果文件是返回目录
		if(file.isDirectory()) {
			return true;
		}
		
		// 返回带扩展名的文件名
		return file.getName().endsWith(extension);
	}
	
	@Override
	public String getDescription() {
		// 返回到显示文件类型和描述
		return description + String.format(" (*%s)", extension);
	}
}
