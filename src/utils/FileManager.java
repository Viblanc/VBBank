package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class FileManager {
	private static final ObjectMapper jsonMapper = new ObjectMapper().registerModule(new JavaTimeModule());
	private static final ObjectMapper xmlMapper = new XmlMapper().registerModule(new JavaTimeModule());
	
	private FileManager() {}
	
	public static <T> List<T> loadJsonFile(String path, Class<T> clazz) {
		List<T> list = new ArrayList<>();
		try (BufferedReader in = Files.newBufferedReader(Paths.get(path))) {
			String data = in.lines().collect(Collectors.joining(System.lineSeparator()));
			JavaType javaType = jsonMapper.getTypeFactory().constructParametricType(List.class, clazz);
			return jsonMapper.readValue(data, javaType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static <T> List<T> loadXmlFile(String path, Class<T> clazz) {
		List<T> list = new ArrayList<>();
		try (BufferedReader in = Files.newBufferedReader(Paths.get(path))) {
			String data = in.lines().collect(Collectors.joining(System.lineSeparator()));
			JavaType javaType = xmlMapper.getTypeFactory().constructParametricType(List.class, clazz);
			return xmlMapper.readValue(data, javaType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static <T> void saveAsJson(List<T> data, String filename) {
		try {
			jsonMapper.writeValue(new File(filename), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static <T> void saveAsXml(T data, String filename) {
		try {
			xmlMapper.writeValue(new File(filename), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
