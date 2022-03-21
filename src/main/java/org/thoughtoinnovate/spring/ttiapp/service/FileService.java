package org.thoughtoinnovate.spring.ttiapp.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thoughtoinnovate.spring.ttiapp.entity.MyFile;

import java.io.IOException;

@Service
@EnableAsync
public class FileService {

    private GridFsTemplate gridFsTemplate;
    private GridFsOperations gridFsOperations;

    public FileService(GridFsTemplate gridFsTemplate, GridFsOperations gridFsOperations) {
        this.gridFsTemplate = gridFsTemplate;
        this.gridFsOperations = gridFsOperations;
    }

    @Async
    public String storeFile(String title, MultipartFile multipartFile) throws IOException {

        DBObject metaData = new BasicDBObject();
        metaData.put("type", "file");
        metaData.put("title", title);
        ObjectId objectId = gridFsTemplate.store(multipartFile.getInputStream(), multipartFile.getName(), multipartFile.getContentType(), metaData);

        return objectId.toString();

    }

    public MyFile getFile(String id) throws IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        MyFile myFile = new MyFile(file.getMetadata().get("title").toString(), gridFsOperations.getResource(file).getInputStream());
        return myFile;
    }
}
