package com.xxxx.server.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @className: CustomAuthorityDeserialize
 * @copyright: HTD
 * @description: 自定义 json 序列化
 *              自定义 Authority 解析器
 *              反序列化方法
 * @author: 里天者
 * @date: 2022/3/22
 * @version: 1.0
 */
public class CustomAuthorityDeserialize extends JsonDeserializer {

	@Override  // 反序列化方法 json 解析   JsonParser json解析
	public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException,
			JsonProcessingException {
		ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
		JsonNode jsonNode = mapper.readTree( jsonParser );
		List<GrantedAuthority> grantedAuthorities = new LinkedList<>(  );
		Iterator<JsonNode> elements = jsonNode.elements();   // 跌代器  元素
		while (elements.hasNext()) {
			JsonNode next = elements.next();
			JsonNode authority = next.get( "authority" );
			grantedAuthorities.add( new SimpleGrantedAuthority( authority.asText() ) );
		}
		return grantedAuthorities;
	}
}
