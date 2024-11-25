package com.example.trip.service;

import com.example.trip.dto.Item;
import com.example.trip.dto.NaverSearch;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemApiService {
    private final NaverSearch naverSearch;

    public List<Item> searchItem(String query){
        //naverSearch클래스의 search()메서드를 호출하면서 검색어(query)를 매개변수로 전달
        String result = naverSearch.search(query);
        //검색어로 검색한 결과를 fromJSONtoItems메서드로 전달
        return fromJSONtoItems(result);
    }

    //JSON형식의 응답 문자열을 ItemApi클래스 형식의 배열로 변환하는 메서드
    public List<Item> fromJSONtoItems(String result){
        //JSONObject : 매개변수 result로 전달받은 문자열을 json데이터로 변환하는 객체(key, value속성을 가지고 있음.)
        JSONObject json = new JSONObject(result);
        //json데이터에서 key값으로 json배열을 만들어서 items에 저장
        JSONArray items = json.getJSONArray("items");
        //key, value가 없는 일반 배열 선언
        List<Item> itemList = new ArrayList<>();
        //json배열의 데이터 개수만큼 반복
        for(int i=0; i<items.length(); i++){
            //items배열의 데이터를 하나씩 가져와서 itemJson에 저장
            JSONObject itemJson = items.getJSONObject(i);
            //HTMl 태그 제거
            String title = Jsoup.parse(itemJson.getString("title")).text();
            //itemJson에 저장된 json데이터에서 title, link, image, lprice, mallName, brand, category1, category2, category3, category4정보만 itemApi에 저장
            Item item = new Item();
            item.setTitle(title);
            item.setLink(itemJson.getString("link"));
            item.setImage(itemJson.getString("image"));
            item.setLprice(itemJson.getInt("lprice"));
            item.setMallName(itemJson.getString("mallName"));
            item.setCategory1(itemJson.getString("category1"));
            item.setCategory2(itemJson.getString("category2"));
            item.setCategory3(itemJson.getString("category3"));
            //itemList배열에 itemApi객체 추가
            itemList.add(item);
        }
        return itemList;
    }
}
