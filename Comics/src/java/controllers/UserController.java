/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import models.User;

/**
 *
 * @author SHD
 */
public class UserController extends HttpServlet {
   
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = extractAction(request);
        switch (action) {
            case "list":
                listUsers(request, response);
                break;
            case "view":
                viewUser(request, response);
                break;
            case "edit":
                editUser(request, response);
                break;
            case "delete":
                deleteUser(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            default:
                listUsers(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = extractAction(request);
        switch (action) {
            case "add":
                addUser(request, response);
                break;
            case "edit":
                updateUser(request, response);
                break;
            default:
                listUsers(request, response);
        }
    }

    private String extractAction(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return "list";
        }
        return pathInfo.substring(1); // Remove leading slash
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> users = userDAO.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/user/list.jsp").forward(request, response);
    }

    private void viewUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        User user = userDAO.getUserById(userId);
        if (user != null) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("/user/view.jsp").forward(request, response);
        } else {
            // Handle case where user with given ID is not found
            response.sendRedirect("error.jsp"); // Example redirect to error page
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to add user form page
        request.getRequestDispatcher("/user/add.jsp").forward(request, response);
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

        User newUser = new User(0, userName, email, password, role, isActive);
        boolean success = userDAO.addUser(newUser);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/users");
        } else {
            request.setAttribute("message", "Failed to add user.");
            request.getRequestDispatcher("/user/add.jsp").forward(request, response);
        }
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        User user = userDAO.getUserById(userId);
        if (user != null) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("/user/edit.jsp").forward(request, response);
        } else {
            // Handle case where user with given ID is not found
            response.sendRedirect("error.jsp"); // Example redirect to error page
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

        User updatedUser = new User(userId, userName, email, password, role, isActive);
        boolean success = userDAO.updateUser(updatedUser);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/users");
        } else {
            request.setAttribute("message", "Failed to update user.");
            request.getRequestDispatcher("/user/edit.jsp").forward(request, response);
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        boolean success = userDAO.deleteUser(userId);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/users");
        } else {
            request.setAttribute("message", "Failed to delete user.");
            request.getRequestDispatcher("/user/list.jsp").forward(request, response);
        }
    }

}
