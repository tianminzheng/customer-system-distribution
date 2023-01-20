package org.geekbang.projects.cs.search.utils;

import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighlightUtils {


    /**
     * 功能：根据字段拼接高亮查询语句
     * @param fields
     * @param preTags
     * @param postTags
     * @return
     */
    public static HighlightBuilder highlightByField(List<String> fields, String preTags, String postTags){
        HighlightBuilder highlightBuilder= new HighlightBuilder(); //生成高亮查询器
        for (String field : fields) {
            highlightBuilder.field(field); //高亮查询字段
        }
        highlightBuilder.preTags(preTags); //高亮设置
        highlightBuilder.postTags(postTags);
        return highlightBuilder;
    }

    /**
     * 功能：查询高亮字段
     * @param documentFields
     * @param field
     * @return
     */
    public static Text[] highlightFieldSByEs(SearchHit documentFields,String field){
        Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();
        HighlightField highlightField = highlightFields.get(field);
        if (highlightField == null) {
            return null;
        }
        return highlightField.getFragments();
    }

    /**
     * 功能：查询高亮字段
     * @param documentFields
     * @param fields
     * @return
     */
    public static HashMap<String,String> highlightMapSByEs(SearchHit documentFields,List<String> fields){
        Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();
        HashMap hashMap = new HashMap<String,String>();
        for (String field : fields) {
            HighlightField highlightField = highlightFields.get(field);
            if (highlightField != null) {
                Text[] fragments = highlightField.getFragments();
                if (fragments != null) {
                    hashMap.put(field, String.valueOf(fragments[0]));
                }

            }

        }

        return hashMap;
    }

}
