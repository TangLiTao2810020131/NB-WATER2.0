package com.ets.business.fileupload.dao;

import com.ets.business.fileupload.entity.FileUpload;

import java.util.List;
import java.util.Map;


public interface FileUploadDao {

    public void addFileRecord(FileUpload fu);

    public FileUpload findUrlByNewTime();

    public List<FileUpload> getDatas(Map<String, Object> map);

    public long getCount(Map<String, Object> map);

}
