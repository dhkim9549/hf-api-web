<%@ page language="java" contentType="application/json; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ page import="java.util.Scanner"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.nio.charset.StandardCharsets"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Date"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="kr.co.direa.server.cruzlink.adapter.api.client.CruzClient"%>
<%@ page import="kr.co.direa.server.cruzlink.adapter.api.exception.CruzAPIException"%>
<%!
private static String CHID = "TSTO01"; //채널ID
private static String URL = "https://esb-dev"; // 개발계 URL
private static CruzClient adapter = null;
%>
<%!
  public static CruzClient getAdapter(){     //단일 객체 사용을 위해 싱글톤 메소드 사용
    if(adapter == null){
      adapter = new CruzClient(CHID, URL, 20);
    }
    return adapter;
  }
%>
<%!
	public static String getGlobId (String envInfoDvcd) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String currTm = sdf.format(new Date());
		String tlgmGid = currTm + //전문작성일자(17)
						envInfoDvcd + //시스템환경구분코드(1)
						"ESB" + //연계시스템코드(3)
						"OPA" + //송신시스템코드(3)
						"ONLE" + //송신업무분류코드(4)
						"00"; //일련번호(2)
		return tlgmGid;
	}
%>
<%!
  public static String esbTest(HttpServletRequest request) throws Exception {
    
    byte[] responseData = null;
    String result = "";
    
    try {
      
      Gson gson = new Gson();

      HashMap<String, Object> headerMap = new HashMap<String, Object>();
      
      HashMap<String, Object> uiCommonMap = new HashMap<String, Object>();
      uiCommonMap.put("scrnId", "_");
      uiCommonMap.put("epno", "99999");
      uiCommonMap.put("brofCd", "WEB");
      headerMap.put("uiCommon", uiCommonMap);
      
      HashMap<String, Object> trdCommonMap = new HashMap<String, Object>();
      trdCommonMap.put("trdPrcsRsltCd", "0");
      trdCommonMap.put("intfId", "OPAONLE_CORHGIG_EOS_00005");
      trdCommonMap.put("taskTycd", "O");
      headerMap.put("trdCommon", trdCommonMap);

      headerMap.put("reqRespDvcd", "S");
      headerMap.put("sendTaskClcd", "ONLE");
      headerMap.put("reqChnlId", "OPA");
      headerMap.put("sendSysDvcd", "OPA");
      headerMap.put("sysEnvDvcd", "P");
      headerMap.put("service", "hf2_cor.hf2_cor_service.SHGIGESBS002");
      headerMap.put("tlgmGid", getGlobId("P"));
      headerMap.put("langDvcd", "KR");
      headerMap.put("recvSysDvcd", "COR");
      headerMap.put("recvTaskClcd", "HGIG");
      headerMap.put("trdPrcsSeq", "3");
      headerMap.put("asyncDvcd", "S");
      headerMap.put("serviceMethod", "selListJnseRcmd");

      HashMap<String, Object> dtoMap = new HashMap<String, Object>();
      dtoMap.put("rentGrntAmt", request.getParameter("rentGrntAmt"));
      dtoMap.put("addr1", new String(request.getParameter("addr1").getBytes("ISO-8859-1"), "UTF-8"));
      dtoMap.put("addr2", new String(request.getParameter("addr2").getBytes("ISO-8859-1"), "UTF-8"));
      dtoMap.put("age", request.getParameter("age"));
      dtoMap.put("weddStcd", request.getParameter("weddStcd"));
      dtoMap.put("myIncmAmt", request.getParameter("myIncmAmt"));
      dtoMap.put("myTotDebtAmt", request.getParameter("myTotDebtAmt"));
      dtoMap.put("sposAnnlIncmAmt", request.getParameter("sposAnnlIncmAmt"));
      dtoMap.put("sposDebtAmt", request.getParameter("sposDebtAmt"));
      dtoMap.put("ownHsCnt", request.getParameter("ownHsCnt"));
      dtoMap.put("chldCnt", request.getParameter("chldCnt"));
      dtoMap.put("sngpHhldYn", request.getParameter("sngpHhldYn"));
      dtoMap.put("plcyCmnrFinUseYn", request.getParameter("plcyCmnrFinUseYn"));
      dtoMap.put("petySoweSftprYn", request.getParameter("petySoweSftprYn"));
      dtoMap.put("crdtRcvrSprtYn", request.getParameter("crdtRcvrSprtYn"));
      dtoMap.put("soclCnsdCndtYn", request.getParameter("soclCnsdCndtYn"));
      dtoMap.put("dsblHhldYn", request.getParameter("dsblHhldYn"));
      dtoMap.put("ntonMrorHhldYn", request.getParameter("ntonMrorHhldYn"));
      dtoMap.put("kwrcHhldYn", request.getParameter("kwrcHhldYn"));
      dtoMap.put("mlfmYn", request.getParameter("mlfmYn"));

      dtoMap.put("pageNo", "1");
      dtoMap.put("numOfRows", "30");

      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("header", headerMap);
      map.put("dto", dtoMap);
      
      String sndData = gson.toJson(map);
      
      CruzClient adapter = getAdapter();
      responseData = adapter.sendHttp(sndData.getBytes("UTF-8"));
      result += new String(responseData, StandardCharsets.UTF_8);
      if(responseData != null){
        //정상 응답 처리
      } else {
       //오류 처리
      }
    } catch (CruzAPIException e) {
      System.out.println("오류 발생. ERR CODE["+e.getErrCode()+"] ERR MSG["+e.getErrMsg()+"]");
    }
    
    return result;
  }
%>
<%
    String result = esbTest(request);
    out.write(result);
%>
