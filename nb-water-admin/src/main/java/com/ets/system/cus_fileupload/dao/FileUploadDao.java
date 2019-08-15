package com.ets.system.cus_fileupload.dao;

import java.util.List;
import java.util.Map;

import com.ets.system.cus_fileupload.entity.FileUpload;


public interface FileUploadDao {

    public void addFileRecord(FileUpload fu);

    public FileUpload findUrlByNewTime();

    public List<FileUpload> getDatas(Map<String, Object> map);

    public long getCount(Map<String, Object> map);

}
