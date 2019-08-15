package com.ets.business.fileupload.service;

import com.ets.business.fileupload.dao.FileUploadDao;
import com.ets.business.fileupload.entity.FileUpload;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class FileUploadService {

    @Resource
    FileUploadDao dao;

    public void addFileRecord(FileUpload fu)
    {
        dao.addFileRecord(fu);
    }

    public FileUpload findUrlByNewTime()
    {
        return dao.findUrlByNewTime();
    }

    public List<FileUpload> getDatas(Map<String, Object> map)
    {
        return dao.getDatas(map);
    }

    public long getCount(Map<String, Object> map)
    {
        return dao.getCount(map);
    }

}
