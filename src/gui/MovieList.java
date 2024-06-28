package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MovieList extends JFrame {
    public MovieList() {
        setTitle("영화 목록");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 4, 10, 10)); // 그리드 레이아웃으로 영화 목록을 표시
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);

        // 이미지 경로 리스트
        List<String> imagePaths = List.of(
            "D:\\2-1\\JAVA\\Project1\\Project2\\src\\imge\\image1.jpg",
            "D:\\2-1\\JAVA\\Project1\\Project2\\src\\imge\\image2.jpg",
            "D:\\2-1\\JAVA\\Project1\\Project2\\src\\imge\\image3.jpg",
            "D:\\2-1\\JAVA\\Project1\\Project2\\src\\imge\\image4.jpg",
            "D:\\2-1\\JAVA\\Project1\\Project2\\src\\imge\\image5.jpg",
            "D:\\2-1\\JAVA\\Project1\\Project2\\src\\imge\\image6.jpg",
            "D:\\2-1\\JAVA\\Project1\\Project2\\src\\imge\\image7.jpg",
            "D:\\2-1\\JAVA\\Project1\\Project2\\src\\imge\\image8.jpg"
        );

        // 영화 제목 리스트
        List<String> movieTitles = List.of(
            "설계자",
            "아이들",
            "어바웃 타임",
            "해리포터",
            "원더랜드",
            "인사이드 아웃2",
            "사운드 오브 뮤직",
            "타겟"
        );

        // 영화 패널 생성 및 추가
        for (int i = 0; i < 8; i++) {
            panel.add(createMoviePanel(movieTitles.get(i), imagePaths.get(i)));
        }

        add(new JScrollPane(panel));
    }

    private JPanel createMoviePanel(String title, String imagePath) {
        JPanel moviePanel = new JPanel(new BorderLayout());
        moviePanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 14));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Load and scale the image
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);

        // Add a mouse listener to the image label to handle clicks
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open the booking window when the image is clicked
                new BookingWindow(title, imagePath).setVisible(true);
                dispose(); // Close the movie list window
            }
        });

        moviePanel.add(imageLabel, BorderLayout.CENTER);
        moviePanel.add(titleLabel, BorderLayout.SOUTH);

        moviePanel.setBorder(BorderFactory.createLineBorder(new Color(44, 62, 80), 1));

        return moviePanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MovieList().setVisible(true);
        });
    }
}
