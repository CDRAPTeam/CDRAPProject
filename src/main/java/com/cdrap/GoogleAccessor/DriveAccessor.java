package com.cdrap.GoogleAccessor;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
public class DriveAccessor {
	private Drive driver;
	private SheetHandler sheets;
	public DriveAccessor(SheetHandler handler) throws IOException{
		sheets = handler;
		driver = DriveEssentials.getDriveService();
	}

	
	public synchronized ArrayList<SheetFileContainer> getFileReferences() throws IOException{
		ArrayList<SheetFileContainer> ret = new ArrayList();
		FileList result = driver.files().list().setPageSize(1000)
					.setQ("mimeType='application/vnd.google-apps.spreadsheet'")//filter
					.setFields(("nextPageToken, files(id, name)")).execute(); //get these
		List<File> files = result.getFiles();
		if(files == null || files.isEmpty()){
			
		}
		else{
			for(File f:files){
				SheetFileContainer container = this.sheets.checkFile(f.getId());
				if(container!=null){
					ret.add(container);
				}
			}
		}
		return ret;
	}
}
