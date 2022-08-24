import java.io.Serializable;
import java.util.*;

public class JnseGrntRcmd {

  public static void main(String[] args) {
    
    JnseGrntRcmd igr = new JnseGrntRcmd();
    
    TreeMap map = new TreeMap();
    
    //////////////////////////////////////////////////////////
    // 입력값 세팅
    map.put("rent_grnt_amt", 200000000L); // 임차보증금액
    map.put("addr1", "서울특별시"); // 주소1
    map.put("addr2", "강남구"); // 주소2

    map.put("age", 20L); // 연령
    map.put("wedd_stcd", "1"); // 결혼상태코드
    map.put("my_incm_amt", 123489L); // 본인소득금액
    map.put("my_tot_debt_amt", 0L); // 본인총부채금액
    map.put("spos_annl_incm_amt", 100000L); // 배우자연간소득금액
    map.put("spos_debt_amt", 0L); // 배우자부채금액
    
    map.put("own_hs_cnt", 0L); // 보유주택수
    map.put("chld_cnt", 0L); // 자녀수
    map.put("sngp_hhld_yn", "N"); // 한부모가구여부
    map.put("plcy_cmnr_fin_use_yn", "N"); // 정책서민금융이용여부
    map.put("pety_sowe_sftpr_yn", "N"); // 영세자영업자여부
    map.put("crdt_rcvr_sprt_yn", "N"); // 신용회복지원여부
    map.put("socl_cnsd_cndt_yn", "N"); // 사회적배려대상자여부
    map.put("dsbl_hhld_yn", "N"); // 장애인가구여부
    map.put("nton_mror_hhld_yn", "N"); // 국가유공자가구여부
    map.put("kwrc_hhld_yn", "N"); // 의사상자가구여부
    map.put("mlfm_yn", "N"); // 다문화가정여부
    
    System.out.println("map = " + map);
    Set rsltSet = igr.getJnseGrntRcmd(map);
    System.out.println("rsltSet = " + rsltSet);
    
  }
  
  ////////////////////////////////////////////////////////////////////////////////////////
	// 전세보증추천
	public HashSet<TreeMap<String, Serializable>> getJnseGrntRcmd(TreeMap<String, Serializable> map) {
	    
	  long rent_grnt_amt = (Long)map.get("rent_grnt_amt");
	  long my_incm_amt = (Long)map.get("my_incm_amt");
	  long spos_annl_incm_amt = (Long)map.get("spos_annl_incm_amt");
	  long own_hs_cnt = (Long)map.get("own_hs_cnt");
	  long age = (Long)map.get("age");
	  long chld_cnt = (Long)map.get("chld_cnt");
	  String addr1 = (String)map.get("addr1");
	  String wedd_stcd = (String)map.get("wedd_stcd");
	  String socl_cnsd_cndt_yn = (String)map.get("socl_cnsd_cndt_yn");
	  
	  HashSet<TreeMap<String, Serializable>> rsltSet = new HashSet<TreeMap<String, Serializable>>();
	  
	  if((((addr1.indexOf("서울") >= 0 || addr1.indexOf("경기") >= 0 || addr1.indexOf("인천") >= 0) && rent_grnt_amt <= 700000000L)
	       || rent_grnt_amt <= 500000000L
	     )
	     && age >= 19
	     && socl_cnsd_cndt_yn.equals("Y")
	     && own_hs_cnt <= 1
	    ) {
	    TreeMap<String, Serializable> rsltMap = new TreeMap<String, Serializable>();
	    rsltMap.put("grnt_dvcd", "2C");
	    rsltMap.put("prod_nm", "사회적배려대상자 특례보증 전세자금대출");
	    rsltMap.put("part_grnt_rate", 100L);
	    rsltMap.put("cont1",
	      "국민기초생활수급자, 차상위계층, 중증장애인, 북한이탈주민, 자립아동, 소년소녀가정, 다문화가정, 노부모부양가정, 영구임대주택입주자, 소액임차인에 대해"
	      + " 한국주택금융공사의 보증서를 담보로 최대 3천만원(채권보전조치 시 45백만원)까지 지원하는 전세자금대출"
	      + "\n\n문의처: 1688-8114(공사콜센터)"
	      + "\n\n예상 보증료율: 0.02(최저보증료율)"
	    );
	    rsltMap.put("cont2",
	      "자세한 사항은 한국주택금융공사 홈페이지(https://www.hf.go.kr)"
	      + "\n>주택보증>전세자금보증(특례)>청년전용"
	    );
	    rsltSet.add(rsltMap);
	  }
	  
	  if((((addr1.indexOf("서울") >= 0 || addr1.indexOf("경기") >= 0 || addr1.indexOf("인천") >= 0) && rent_grnt_amt <= 700000000L)
	       || rent_grnt_amt <= 500000000L
	     )
	     && age >= 19
	     && (own_hs_cnt <= 0
	         || own_hs_cnt <= 1 && (my_incm_amt + spos_annl_incm_amt) <= 70000000L
	        )
	     && own_hs_cnt <= 1
	    ) {
	    TreeMap<String, Serializable> rsltMap = new TreeMap<String, Serializable>();
	    rsltMap.put("grnt_dvcd", "2D");
	    rsltMap.put("prod_nm", "일반전세자금대출(한국주택금융공사 보증서담보)");
	    rsltMap.put("part_grnt_rate", 90L);
	    rsltMap.put("cont1",
	      "주택에 전세로 입주하는 고객이나 기존에 전세로 거주한 고객 중 재계약자를 대상으로, 부족한 전세자금 지원을 위하여 한국주택금융공사의 보증서를 담보로 최대 222백만원까지 지원하는 전세자금대출"
	      + "\n\n문의처: 1688-8114(공사콜센터)"
	      + "\n\n예상 보증료율: 0.02(최저보증료율)"
	    );
	    rsltMap.put("cont2",
	      "자세한 사항은 한국주택금융공사 홈페이지(https://www.hf.go.kr)"
	      + "\n>주택보증>전세자금보증>일반전세"
	    );
	    rsltSet.add(rsltMap);
	  }

	  if((((addr1.indexOf("서울") >= 0 || addr1.indexOf("경기") >= 0 || addr1.indexOf("인천") >= 0) && rent_grnt_amt <= 700000000L)
	       || rent_grnt_amt <= 500000000L
	     )
	     && (age >= 19 && age < 34)
	     && (my_incm_amt + spos_annl_incm_amt) <= 70000000L
	     && own_hs_cnt <= 0
	    ) {
	    TreeMap<String, Serializable> rsltMap = new TreeMap<String, Serializable>();
	    rsltMap.put("grnt_dvcd", "2V");
	    rsltMap.put("prod_nm", "청년맞춤형 전세자금대출");
	    rsltMap.put("part_grnt_rate", 100L);
	    rsltMap.put("cont1",
	      "청년층 주거비용을 경감하기 위해 한국주택금융공사의 보증서를 담보로 최대 1억원까지 지원하는 청년 전용 전세자금 대출"
	      + "\n\n문의처: 1688-8114(공사콜센터)"
	      + "\n\n예상 보증료율: 0.02(최저보증료율)"
	    );
	    rsltMap.put("cont2",
	      "자세한 사항은 한국주택금융공사 홈페이지(https://www.hf.go.kr)"
	      + "\n>주택보증>전세자금보증(특례)>청년전용"
	    );
	    rsltSet.add(rsltMap);
	  }

	  if((((addr1.indexOf("서울") >= 0 || addr1.indexOf("경기") >= 0 || addr1.indexOf("인천") >= 0) && rent_grnt_amt <= 700000000L)
	       || rent_grnt_amt <= 500000000L
	     )
	     && age >= 19
	     && (Arrays.asList("3","4").contains(wedd_stcd) || chld_cnt >= 2)
	     && (my_incm_amt + spos_annl_incm_amt) <= 100000000L
	     && own_hs_cnt <= 1
	    ) {
	    TreeMap<String, Serializable> rsltMap = new TreeMap<String, Serializable>();
	    rsltMap.put("grnt_dvcd", "41");
	    rsltMap.put("prod_nm", "금융기관 신혼부부,다자녀 협약 대출");
	    rsltMap.put("part_grnt_rate", 100L);
	    rsltMap.put("cont1",
	      "신혼부부 또는 결혼예정자, 다자녀가구를 대상으로 금융기관이 대출 금리를 인하하고, 한국주택금융공사의 보증서를 담보로 최대 2억원까지 지원하는 전세자금대출"
	      + "\n\n문의처"
	      + "\n하나은행(1599-2222)"
	      + "\nKB국민은행(1599-9999)"
	      + "\n\n예상 보증료율: 0.02(최저보증료율)"
	    );
	    rsltMap.put("cont2",
	      "자세한 사항은 한국주택금융공사 홈페이지(https://www.hf.go.kr)"
	      + "\n>주택보증>전세자금보증(협약)>신혼, 다자녀, 다문화가구"
	    );
	    rsltSet.add(rsltMap);
	  }
	  
	  if(rent_grnt_amt <= 700000000L
	     && addr1.indexOf("서울") >= 0
	     && age >= 19
	     && Arrays.asList("3","4").contains(wedd_stcd)
	     && (my_incm_amt + spos_annl_incm_amt) <= 97000000L
	     && own_hs_cnt <= 0
	    ) {
	    TreeMap<String, Serializable> rsltMap = new TreeMap<String, Serializable>();
	    rsltMap.put("grnt_dvcd", "43");
	    rsltMap.put("prod_nm", "서울시 신혼부부 전세자금대출");
	    rsltMap.put("part_grnt_rate", 100L);
	    rsltMap.put("cont1",
	      "서울특별시의 융자추천에 선정된 신혼부부 또는 결혼예정자를 대상으로 이자를 지원하고 한국주택금융공사의 보증서를 담보로 최대 2억원까지 지원하는 맞춤형 전세자금 대출"
	      + "\n\n문의처"
	      + "\n서울 다산콜센터(02-120)또는 주거복지지원센터"
	      + "\n하나은행(1599-2222)"
	      + "\nKB국민은행(1599-9999)"
	      + "\n신한은행(1577-8000)"
	      + "\n\n예상 보증료율: 0.02(최저보증료율)"
	    );
	    rsltMap.put("cont2",
	      "자세한 사항은 서울주거포털(https://housing.seoul.go.kr)>주거정책>청년·신혼부부 지원>신혼부부 임차보증금 지원 참고"
	    );
	    rsltSet.add(rsltMap);
	  }

	  if(rent_grnt_amt <= 300000000L
	     && addr1.indexOf("서울") >= 0
	     && (age >= 19 && age <= 39)
	     && (Arrays.asList("2","3","4").contains(wedd_stcd) && (my_incm_amt + spos_annl_incm_amt) <= 50000000L
	         || (my_incm_amt + spos_annl_incm_amt) <= 40000000L
	        )
	     && own_hs_cnt <= 0
	    ) {
	    TreeMap<String, Serializable> rsltMap = new TreeMap<String, Serializable>();
	    rsltMap.put("grnt_dvcd", "4J");
	    rsltMap.put("prod_nm", "서울시 청년 전세자금대출");
	    rsltMap.put("part_grnt_rate", 100L);
	    rsltMap.put("cont1",
	      "서울특별시의 융자추천에 선정된 사회초년생 및 취업준비생 을 대상으로 이자를 지원하고 한국주택금융공사의 보증서를 담보로 최대 7천만원까지 지원하는 전세자금대출"
	      + "\n\n문의처"
	      + "\n서울 다산콜센터(02-120)또는 주거복지지원센터"
	      + "\n하나은행(1599-2222)"
	      + "\n\n예상 보증료율: 0.02(최저보증료율)"
	    );
	    rsltMap.put("cont2",
	      "자세한 사항은 서울주거포털(https://housing.seoul.go.kr)>주거정책>청년·신혼부부 지원>청년임차보증금 지원 참고"
	    );
	    rsltSet.add(rsltMap);
	  }
	  
	  return rsltSet;
	}
  
}