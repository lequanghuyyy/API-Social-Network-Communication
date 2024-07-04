package com.springboot.cms.repository.Impl;

import com.springboot.cms.dto.ContentDto;
import com.springboot.cms.entity.ContentEntity;
import com.springboot.cms.entity.MemberEntity;
import com.springboot.cms.entity.UserEntity;
import com.springboot.cms.repository.ContentCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository("contentNativeQueryCustomRepository")
public class ContentNativeQueryCustomRepository implements ContentCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ContentEntity> search(String keySearch, int page, int pageSize) {
        StringBuilder sql1 = new StringBuilder(
                "select content.id, content.brief, content.category, content.content, content.title, content.create_date, content.update_time, content.author_id, " +
                        "member.age, member.first_name, member.last_name, member.user_id " +
                        "from content, member where content.author_id = member.id");
        List<Object> parameters = new ArrayList<>();

        if (StringUtils.isNotBlank(keySearch)) {
            sql1.append(" and ( content like ? or title like ? or brief like ? )");
            parameters.add("%" + keySearch + "%");
            parameters.add("%" + keySearch + "%");
            parameters.add("%" + keySearch + "%");
        }

        sql1.append(" LIMIT ? OFFSET ?");
        parameters.add(pageSize);
        parameters.add((page - 1) * pageSize);

        Query query = entityManager.createNativeQuery(sql1.toString());
        for (int i = 0; i < parameters.size(); i++) {
            query.setParameter(i + 1, parameters.get(i));
        }

        return convertToContentList(query.getResultList());
    }

    @Override
    public long count(String keySearch) {
        StringBuilder sql = new StringBuilder(
                "select count(*) from content where 1=1");
        List<Object> parameters = new ArrayList<>();

        if (StringUtils.isNotBlank(keySearch)) {
            sql.append(" and ( content like ? or title like ? or brief like ? )");
            parameters.add("%" + keySearch + "%");
            parameters.add("%" + keySearch + "%");
            parameters.add("%" + keySearch + "%");
        }

        Query query = entityManager.createNativeQuery(sql.toString());

        for (int i = 0; i < parameters.size(); i++) {
            query.setParameter(i + 1, parameters.get(i));
        }

        return ((Number) query.getSingleResult()).longValue();
    }

    public static List<ContentEntity> convertToContentList(List<Object[]> contentDtoList) {
        List<ContentEntity> contentList = new ArrayList<>();
        for (Object[] objects : contentDtoList) {
            ContentEntity contentEntity = new ContentEntity();
            contentEntity.setId((Integer) objects[0]);
            contentEntity.setBrief((String) objects[1]);
            contentEntity.setCategory((String) objects[2]);
            contentEntity.setContent((String) objects[3]);
            contentEntity.setTitle((String) objects[4]);
            contentEntity.setCreateDate((Date) objects[5]);
            contentEntity.setUpdateTime((Date) objects[6]);
            Integer authorId = (Integer) objects[7];
            MemberEntity memberEntity = new MemberEntity();
            UserEntity userEntity = new UserEntity();
            memberEntity.setId(authorId);
            memberEntity.setAge((Integer) objects[8]);
            memberEntity.setFirstName((String) objects[9]);
            memberEntity.setLastName((String) objects[10]);
            userEntity.setId((Long) objects[11]);
            memberEntity.setUserEntity(userEntity);
            contentEntity.setMember(memberEntity);
            contentList.add(contentEntity);
        }
        return contentList;
    }
}
