/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import dao.StoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import models.Story;

/**
 *
 * @author SHD
 */
public class StoryController extends HttpServlet {
   
   private StoryDAO storyDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        storyDAO = new StoryDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = extractAction(request);
        switch (action) {
            case "list":
                listStories(request, response);
                break;
            case "view":
                viewStory(request, response);
                break;
            case "edit":
                editStory(request, response);
                break;
            case "delete":
                deleteStory(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            default:
                listStories(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = extractAction(request);
        switch (action) {
            case "add":
                addStory(request, response);
                break;
            case "edit":
                updateStory(request, response);
                break;
            default:
                listStories(request, response);
        }
    }

    private String extractAction(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return "list";
        }
        return pathInfo.substring(1); // Remove leading slash
    }

    private void listStories(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Story> stories = storyDAO.getAllStories();
        request.setAttribute("stories", stories);
        request.getRequestDispatcher("/story/list.jsp").forward(request, response);
    }

    private void viewStory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int storyId = Integer.parseInt(request.getParameter("storyId"));
        Story story = storyDAO.getStoryById(storyId);
        if (story != null) {
            request.setAttribute("story", story);
            request.getRequestDispatcher("/story/view.jsp").forward(request, response);
        } else {
            // Handle case where story with given ID is not found
            response.sendRedirect("error.jsp"); // Example redirect to error page
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to add story form page
        request.getRequestDispatcher("/story/add.jsp").forward(request, response);
    }

    private void addStory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        int authorId = Integer.parseInt(request.getParameter("authorId"));
        int genreId = Integer.parseInt(request.getParameter("genreId"));
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        // Assuming createdAt is automatically set in the database
        // Timestamp createdAt = new Timestamp(System.currentTimeMillis());
        String image = request.getParameter("image");

        Story newStory = new Story(0, title, description,authorId, genreId, status, null, image);
        boolean success = storyDAO.addStory(newStory);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/stories");
        } else {
            request.setAttribute("message", "Failed to add story.");
            request.getRequestDispatcher("/story/add.jsp").forward(request, response);
        }
    }

    private void editStory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int storyId = Integer.parseInt(request.getParameter("storyId"));
        Story story = storyDAO.getStoryById(storyId);
        if (story != null) {
            request.setAttribute("story", story);
            request.getRequestDispatcher("/story/edit.jsp").forward(request, response);
        } else {
            // Handle case where story with given ID is not found
            response.sendRedirect("error.jsp"); // Example redirect to error page
        }
    }

    private void updateStory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int storyId = Integer.parseInt(request.getParameter("storyId"));
        String title = request.getParameter("title");
        int authorId = Integer.parseInt(request.getParameter("authorId"));
        int genreId = Integer.parseInt(request.getParameter("genreId"));
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        // Assuming createdAt is automatically set in the database
        // Timestamp createdAt = new Timestamp(System.currentTimeMillis());
        String image = request.getParameter("image");

        Story updatedStory = new Story(storyId, title,description, authorId, genreId, status, null, image);
        boolean success = storyDAO.updateStory(updatedStory);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/stories");
        } else {
            request.setAttribute("message", "Failed to update story.");
            request.getRequestDispatcher("/story/edit.jsp").forward(request, response);
        }
    }

    private void deleteStory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int storyId = Integer.parseInt(request.getParameter("storyId"));
        boolean success = storyDAO.deleteStory(storyId);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/stories");
        } else {
            request.setAttribute("message", "Failed to delete story.");
            request.getRequestDispatcher("/story/list.jsp").forward(request, response);
        }
    }

}
