/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Story;

/**
 *
 * @author SHD
 */
public class StoryDAO extends DBContext{
    public List<Story> getAllStories() {
        List<Story> stories = new ArrayList<>();
        String sql = "SELECT * FROM Stories";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int storyId = rs.getInt("StoryId");
                String title = rs.getString("Title");
                int authorId = rs.getInt("AuthorId");
                int genreId = rs.getInt("GenreId");
                String description = rs.getString("Description");
                String status = rs.getString("Status");
                java.sql.Timestamp createdAt = rs.getTimestamp("CreatedAt");
                String image = rs.getString("Image");
                Story story = new Story(storyId, title,description, authorId, genreId, status, createdAt, image);
                stories.add(story);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return stories;
    }

    

    public boolean addStory(Story story) {
        String sql = "INSERT INTO Stories (Title, AuthorId, GenreId, Description, Status, CreatedAt, Image) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, story.getTitle());
            stm.setInt(2, story.getAuthorID());
            stm.setInt(3, story.getGenreID());
            stm.setString(4, story.getDescription());
            stm.setString(5, story.getStatus());
            stm.setTimestamp(6, story.getCreatedAt());
            stm.setString(7, story.getImage());
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateStory(Story story) {
        String sql = "UPDATE Stories SET Title = ?, AuthorId = ?, GenreId = ?, Description = ?, " +
                     "Status = ?, CreatedAt = ?, Image = ? WHERE StoryId = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, story.getTitle());
            stm.setInt(2, story.getAuthorID());
            stm.setInt(3, story.getGenreID());
            stm.setString(4, story.getDescription());
            stm.setString(5, story.getStatus());
            stm.setTimestamp(6, story.getCreatedAt());
            stm.setString(7, story.getImage());
            stm.setInt(8, story.getStoryId());
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteStory(int storyId) {
        String sql = "DELETE FROM Stories WHERE StoryId = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, storyId);
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public Story getStoryById(int storyId) {
        String sql = "SELECT s.StoryId, s.Title, s.Description, s.AuthorId, s.GenreId, s.Status, s.CreatedAt, " +
                     "a.AuthorName, g.GenreName " +
                     "FROM Stories s " +
                     "INNER JOIN Authors a ON s.AuthorId = a.AuthorId " +
                     "INNER JOIN Genres g ON s.GenreId = g.GenreId " +
                     "WHERE s.StoryId = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, storyId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("StoryId");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                int authorId = rs.getInt("AuthorId");
                int genreId = rs.getInt("GenreId");
                String status = rs.getString("Status");
                java.sql.Timestamp createdAt = rs.getTimestamp("CreatedAt");
                String Image = rs.getString("Image");

                return new Story(id, title, description, authorId, genreId, status, createdAt, Image);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public List<Story> getCompletedStoriesWithImages() {
        List<Story> stories = new ArrayList<>();
        try {
            String sql = "SELECT TOP 1000 StoryId, Title, Description, AuthorId, GenreId, Status, CreatedAt, Image FROM Stories WHERE Status = 'Completed' ORDER BY CreatedAt DESC";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Story story = new Story();
                story.setStoryId(rs.getInt("StoryId"));
                story.setTitle(rs.getString("Title"));
                story.setDescription(rs.getString("Description"));
                story.setAuthorID(rs.getInt("AuthorId"));
                story.setGenreID(rs.getInt("GenreId"));
                story.setStatus(rs.getString("Status"));
                story.setCreatedAt(rs.getTimestamp("CreatedAt"));
                story.setImage(rs.getString("Image"));
                stories.add(story);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stories;
    }

    public List<Story> getNewestStoriesWithImages() {
        List<Story> stories = new ArrayList<>();
        try {
            String sql = "SELECT TOP 1000 StoryId, Title, Description, AuthorId, GenreId, Status, CreatedAt, Image FROM Stories ORDER BY CreatedAt DESC";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Story story = new Story();
                story.setStoryId(rs.getInt("StoryId"));
                story.setTitle(rs.getString("Title"));
                story.setDescription(rs.getString("Description"));
                story.setAuthorID(rs.getInt("AuthorId"));
                story.setGenreID(rs.getInt("GenreId"));
                story.setStatus(rs.getString("Status"));
                story.setCreatedAt(rs.getTimestamp("CreatedAt"));
                story.setImage(rs.getString("Image"));
                stories.add(story);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stories;
    }
}
