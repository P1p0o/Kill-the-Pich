package com.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bang.model.game.GameModel;


public class GameServlet extends HttpServlet {
   
    public GameServlet() {
        super();
    }
    

   @Override
   public void init() throws ServletException {
	   super.init();

   }

	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String token = request.getParameter("token");
	
	}

}