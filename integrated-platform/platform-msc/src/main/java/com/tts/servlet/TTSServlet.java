package com.tts.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tts.util.MscTtsUtil;

/**
 * Servlet implementation class TTSServlet
 */
//@WebServlet("/TTS")
public class TTSServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TTSServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String type = request.getParameter("type");
		if(type==null || "".equals(type)){
			response.sendRedirect("index.jsp");
		}else if ("tts".equals(type)){
			String text = request.getParameter("text");
			String voiceName = request.getParameter("voiceName");
			System.out.println(request.getAttribute("voiceName"));
			try {
				MscTtsUtil.getMscObj().Synthesize(text,voiceName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			response.getWriter().write("success");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
