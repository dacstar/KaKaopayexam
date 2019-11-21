package com.kakao.kakaoexam.serviceImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.kakaoexam.Dto.SupplyDto;
import com.kakao.kakaoexam.entity.HousesupplyEntity;
import com.kakao.kakaoexam.repository.HousesupplyRespository;
import com.kakao.kakaoexam.service.IFinanceService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import ch.qos.logback.core.net.server.ConcurrentServerRunner;
@Service
public class FinanceService implements IFinanceService{
	
	
	
	@Autowired
	private HousesupplyRespository housesupplyRespository;
	
	//은행갯수
    private final int bank= 11;

    
	@Override
	public int putDate() {
		// TODO Auto-generated method stub
		int status=1;
		
		//CSV 파일 입력 받아서 내용을  HousesupplyEntity에 저장
		try {
			BufferedReader data = new BufferedReader(new FileReader("src/main/resources/static/사전과제3.csv"));
			
			while (data.readLine() != null) {
                
				CSVReader reder = new CSVReader(data);
				List<String[]> list = reder.readAll();
				

				for(String[] str : list) {
					String[] str2= new String[bank];
					for(int i=0;i<bank;i++) {
					str2[i]=str[i];
					    System.out.print(str2[i]+" ");					
					}
					System.out.println();
					
                    housesupplyRespository.save(new HousesupplyEntity(str2));
                }

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status=0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CsvException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}
	//
	@Override
	public List<HousesupplyEntity> getSupplyList() {
		// TODO Auto-generated method stub
		
		// JPA Select
		
		//when
        Iterable<HousesupplyEntity> supplyInfIter = housesupplyRespository.findAll();
       
        
        //then
        HousesupplyEntity h; 
        
        List<HousesupplyEntity> supplyInfList = new LinkedList<>();
        
        
        supplyInfIter.forEach(supplyInfList::add);
		
//        for(int i=0;i<supplyInfList.size();i++) {
//        	System.out.println(supplyInfList.get(i).toString());
//        }
        
		return supplyInfList;
	}
	
	@Override
	//연도 정보를 통해 연도별 공급 현황가져오기
	public List<HousesupplyEntity> getyearTotal(int year) {
		// TODO Auto-generated method stub
		 List<HousesupplyEntity> supplyInfList = housesupplyRespository.findByyear(year);

		return supplyInfList;
	}
	
	
	

}
