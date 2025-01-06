package com.kh.common.rename;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileRenamePolicy implements com.oreilly.servlet.multipart.FileRenamePolicy {
	
	@Override
	public File rename(File originFile) {
		String originName = originFile.getName();
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		int random = (int)(Math.random()* 90000 + 10000);
		String ext = originName.substring(originName.lastIndexOf("."));
		String changeName = currentTime+random+ext;
		return new File(originFile.getParent(), changeName);
	}
}
