package gui;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import db.DB2;

public class BookingWindow extends JFrame {
    private Set<JCheckBox> selectedSeats = new HashSet<>();
    private JLabel summaryLabel;
    private JRadioButton[] timeRadioButtons;
    private JDateChooser dateChooser;
    private JPanel seatSelectionPanel;
    private JLabel seatCountLabel;
    private JComboBox<String> screenComboBox;
    private JList<String> theaterList;
    private JRadioButton[] adultCountButtons;
    private JRadioButton[] childCountButtons;
    private String movieTitle;
    private String imagePath;

    public BookingWindow(String movieTitle, String imagePath) {
        this.movieTitle = movieTitle;
        this.imagePath = imagePath;
        setTitle("영화 예매");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        topPanel.add(backButton, BorderLayout.WEST);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel movieAndDateTimePanel = new JPanel(new BorderLayout(10, 10));
        movieAndDateTimePanel.setBackground(Color.WHITE);

        JPanel movieSelectionPanel = new JPanel(new BorderLayout());
        movieSelectionPanel.setBackground(Color.WHITE);
        movieSelectionPanel.setBorder(BorderFactory.createTitledBorder("Select Movie"));

        JLabel titleLabel = new JLabel(movieTitle, JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 14));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);

        movieSelectionPanel.add(imageLabel, BorderLayout.CENTER);
        movieSelectionPanel.add(titleLabel, BorderLayout.SOUTH);

        JPanel theaterSelectionPanel = new JPanel(new BorderLayout());
        theaterSelectionPanel.setBackground(Color.WHITE);
        theaterSelectionPanel.setBorder(BorderFactory.createTitledBorder("극장 선택"));

        DefaultListModel<String> theaterListModel = new DefaultListModel<>();
        theaterListModel.addElement("인천점");
        theaterListModel.addElement("연수역");
        theaterListModel.addElement("인천학익");
        theaterListModel.addElement("인천시민공원");
        theaterListModel.addElement("송도타임스페이스");

        theaterList = new JList<>(theaterListModel);
        theaterSelectionPanel.add(new JScrollPane(theaterList), BorderLayout.CENTER);

        JPanel peopleSelectionPanel = new JPanel(new GridLayout(2, 1));
        peopleSelectionPanel.setBackground(Color.WHITE);
        peopleSelectionPanel.setBorder(BorderFactory.createTitledBorder("인원선택"));

        JPanel adultPanel = new JPanel(new GridLayout(1, 6));
        adultPanel.setBackground(Color.WHITE);
        adultPanel.setBorder(BorderFactory.createTitledBorder("성인 (10,000 원)"));
        adultCountButtons = new JRadioButton[6];
        ButtonGroup adultButtonGroup = new ButtonGroup();
        for (int i = 0; i < 6; i++) {
            adultCountButtons[i] = new JRadioButton(String.valueOf(i));
            adultCountButtons[i].setBackground(Color.WHITE);
            adultButtonGroup.add(adultCountButtons[i]);
            adultPanel.add(adultCountButtons[i]);
        }
        adultCountButtons[0].setSelected(true);

        JPanel childPanel = new JPanel(new GridLayout(1, 6));
        childPanel.setBackground(Color.WHITE);
        childPanel.setBorder(BorderFactory.createTitledBorder("청소년 (8,000 원)"));
        childCountButtons = new JRadioButton[6];
        ButtonGroup childButtonGroup = new ButtonGroup();
        for (int i = 0; i < 6; i++) {
            childCountButtons[i] = new JRadioButton(String.valueOf(i));
            childCountButtons[i].setBackground(Color.WHITE);
            childButtonGroup.add(childCountButtons[i]);
            childPanel.add(childCountButtons[i]);
        }
        childCountButtons[0].setSelected(true);

        peopleSelectionPanel.add(adultPanel);
        peopleSelectionPanel.add(childPanel);

        JPanel screenSelectionPanel = new JPanel(new BorderLayout());
        screenSelectionPanel.setBackground(Color.WHITE);
        screenSelectionPanel.setBorder(BorderFactory.createTitledBorder("상영관 선택"));

        screenComboBox = new JComboBox<>(new String[]{"1관", "2관", "3관", "4관"});
        screenSelectionPanel.add(screenComboBox, BorderLayout.CENTER);

        JPanel dateTimeSelectionPanel = new JPanel(new BorderLayout());
        dateTimeSelectionPanel.setBackground(Color.WHITE);
        dateTimeSelectionPanel.setBorder(BorderFactory.createTitledBorder("Select Date and Time"));

        JPanel datePanel = new JPanel(new BorderLayout());
        datePanel.setBackground(Color.WHITE);
        datePanel.add(new JLabel("Date"), BorderLayout.WEST);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        datePanel.add(dateChooser, BorderLayout.CENTER);

        dateTimeSelectionPanel.add(datePanel, BorderLayout.NORTH);

        JPanel timePanel = new JPanel(new GridLayout(3, 2));
        timePanel.setBackground(Color.WHITE);
        timePanel.setBorder(BorderFactory.createTitledBorder("Select Time"));

        String[] timeSlots = {"9:00 ~ 10:47", "12:00 ~ 13:47", "15:00 ~ 16:47", "18:00 ~ 19:47", "21:00 ~ 22:47"};
        timeRadioButtons = new JRadioButton[timeSlots.length];
        ButtonGroup timeButtonGroup = new ButtonGroup();
        for (int i = 0; i < timeSlots.length; i++) {
            timeRadioButtons[i] = new JRadioButton(timeSlots[i]);
            timeRadioButtons[i].setBackground(Color.WHITE);
            timeButtonGroup.add(timeRadioButtons[i]);
            timePanel.add(timeRadioButtons[i]);
        }
        timeRadioButtons[0].setSelected(true);

        dateTimeSelectionPanel.add(timePanel, BorderLayout.CENTER);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(theaterSelectionPanel, BorderLayout.NORTH);
        leftPanel.add(peopleSelectionPanel, BorderLayout.CENTER);
        leftPanel.add(screenSelectionPanel, BorderLayout.SOUTH);

        movieAndDateTimePanel.add(movieSelectionPanel, BorderLayout.WEST);
        movieAndDateTimePanel.add(leftPanel, BorderLayout.CENTER);
        movieAndDateTimePanel.add(dateTimeSelectionPanel, BorderLayout.EAST);

        JPanel seatWrapperPanel = new JPanel(new BorderLayout());
        seatWrapperPanel.setBackground(Color.WHITE);
        seatWrapperPanel.setBorder(BorderFactory.createTitledBorder("Select Seats"));

        seatSelectionPanel = new JPanel(new GridBagLayout());
        seatSelectionPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridy = 0;
        gbc.gridx = 1;
        for (int i = 1; i <= 15; i++) {
            gbc.gridx = i;
            seatSelectionPanel.add(new JLabel(String.valueOf(i), JLabel.CENTER), gbc);
        }

        String[] rows = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};
        for (int r = 0; r < rows.length; r++) {
            gbc.gridx = 0;
            gbc.gridy = r + 1;
            seatSelectionPanel.add(new JLabel(rows[r], JLabel.CENTER), gbc);

            for (int c = 1; c <= 15; c++) {
                gbc.gridx = c;
                JCheckBox seatCheckBox = new JCheckBox();
                seatCheckBox.setBackground(Color.WHITE);
                seatCheckBox.setText(rows[r] + c);
                seatCheckBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (seatCheckBox.isSelected()) {
                            selectedSeats.add(seatCheckBox);
                        } else {
                            selectedSeats.remove(seatCheckBox);
                        }
                        updateSummary(movieTitle);
                        updateSeatCount();
                    }
                });
                seatSelectionPanel.add(seatCheckBox, gbc);
            }
        }

        JScrollPane seatScrollPane = new JScrollPane(seatSelectionPanel);
        seatWrapperPanel.add(new JLabel("Screen", JLabel.CENTER), BorderLayout.NORTH);
        seatWrapperPanel.add(seatScrollPane, BorderLayout.CENTER);
        seatWrapperPanel.add(seatCountLabel = new JLabel("Selected Seats: 0 / 255", JLabel.CENTER), BorderLayout.SOUTH);

        summaryLabel = new JLabel("");
        mainPanel.add(summaryLabel, BorderLayout.SOUTH);

        mainPanel.add(movieAndDateTimePanel, BorderLayout.NORTH);
        mainPanel.add(seatWrapperPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        JButton confirmBookingButton = new JButton("결제하기");
        confirmBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (theaterList.getSelectedValue() == null) {
                    JOptionPane.showMessageDialog(BookingWindow.this, "극장 선택을 해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (dateChooser.getDate() == null) {
                    JOptionPane.showMessageDialog(BookingWindow.this, "날짜를 선택해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int totalPeople = getSelectedAdultCount() + getSelectedChildCount();
                int selectedSeatsCount = selectedSeats.size();

                if (totalPeople == 0) {
                    JOptionPane.showMessageDialog(BookingWindow.this, "인원을 선택해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (totalPeople != selectedSeatsCount) {
                    JOptionPane.showMessageDialog(BookingWindow.this, "선택한 좌석 수와 인원 수가 일치하지 않습니다.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (saveBookingToDatabase()) {
                    JOptionPane.showMessageDialog(BookingWindow.this, "예매가 완료되었습니다.");
                    displayBookingDetails();
                    dispose();
                }
            }
        });
        add(confirmBookingButton, BorderLayout.SOUTH);

        updateSeatCount();

        dateChooser.addPropertyChangeListener("date", e -> {
            updateSummary(movieTitle);
            loadUnavailableSeats();
        });
        for (JRadioButton rb : timeRadioButtons) {
            rb.addActionListener(e -> {
                updateSummary(movieTitle);
                loadUnavailableSeats();
            });
        }
        for (JRadioButton rb : adultCountButtons) {
            rb.addActionListener(e -> updateSummary(movieTitle));
        }
        for (JRadioButton rb : childCountButtons) {
            rb.addActionListener(e -> updateSummary(movieTitle));
        }
    }

    private void loadUnavailableSeats() {
        if (dateChooser.getDate() == null || theaterList.getSelectedValue() == null || screenComboBox.getSelectedItem() == null || getSelectedTime().isEmpty()) {
            return;
        }

        try (Connection conn = DB2.getConnection()) {
            String sql = "SELECT seats FROM movie_booking WHERE movie_title = ? AND theater = ? AND screen = ? AND booking_date = ? AND booking_time = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movieTitle);
            pstmt.setString(2, theaterList.getSelectedValue());
            pstmt.setString(3, screenComboBox.getSelectedItem().toString());
            pstmt.setDate(4, new java.sql.Date(dateChooser.getDate().getTime()));
            pstmt.setString(5, getSelectedTime());

            ResultSet rs = pstmt.executeQuery();
            Set<String> unavailableSeats = new HashSet<>();
            while (rs.next()) {
                String[] seats = rs.getString("seats").split(" ");
                for (String seat : seats) {
                    unavailableSeats.add(seat);
                }
            }

            for (Component comp : seatSelectionPanel.getComponents()) {
                if (comp instanceof JCheckBox) {
                    JCheckBox checkBox = (JCheckBox) comp;
                    if (unavailableSeats.contains(checkBox.getText())) {
                        checkBox.setEnabled(false);
                        checkBox.setBackground(Color.RED);
                    } else {
                        checkBox.setEnabled(true);
                        checkBox.setBackground(Color.WHITE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean saveBookingToDatabase() {
        if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "날짜를 선택해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        java.util.Date selectedDate = dateChooser.getDate();
        java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

        try (Connection conn = DB2.getConnection()) {
            conn.setAutoCommit(false);

            String sql = "INSERT INTO movie_booking (movie_title, theater, screen, booking_date, booking_time, seats, adult_count, child_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movieTitle);
            pstmt.setString(2, theaterList.getSelectedValue());
            pstmt.setString(3, screenComboBox.getSelectedItem().toString());
            pstmt.setDate(4, sqlDate);
            pstmt.setString(5, getSelectedTime());
            pstmt.setString(6, getSelectedSeatsString());
            pstmt.setInt(7, getSelectedAdultCount());
            pstmt.setInt(8, getSelectedChildCount());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new booking was inserted successfully!");
            } else {
                System.out.println("No booking was inserted.");
                return false;
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void displayBookingDetails() {
        try (Connection conn = DB2.getConnection()) {
            String sql = "SELECT * FROM movie_booking WHERE movie_title = ? AND theater = ? AND screen = ? AND booking_date = ? AND booking_time = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movieTitle);
            pstmt.setString(2, theaterList.getSelectedValue());
            pstmt.setString(3, screenComboBox.getSelectedItem().toString());
            pstmt.setDate(4, new java.sql.Date(dateChooser.getDate().getTime()));
            pstmt.setString(5, getSelectedTime());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String details = String.format("Movie: %s\nTheater: %s\nScreen: %s\nDate: %s\nTime: %s\nSeats: %s\nAdult: %d\nChild: %d",
                        rs.getString("movie_title"), rs.getString("theater"), rs.getString("screen"), rs.getDate("booking_date"), rs.getString("booking_time"), rs.getString("seats"), rs.getInt("adult_count"), rs.getInt("child_count"));
                JOptionPane.showMessageDialog(this, details, "Booking Details", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getSelectedSeatsString() {
        if (selectedSeats.isEmpty()) {
            return "No seats selected";
        }

        StringBuilder sb = new StringBuilder();
        for (JCheckBox seat : selectedSeats) {
            sb.append(seat.getText()).append(" ");
        }
        return sb.toString().trim();
    }

    private String getSelectedTime() {
        for (JRadioButton rb : timeRadioButtons) {
            if (rb.isSelected()) {
                return rb.getText();
            }
        }
        return "";
    }

    private int getSelectedAdultCount() {
        for (JRadioButton rb : adultCountButtons) {
            if (rb.isSelected()) {
                return Integer.parseInt(rb.getText());
            }
        }
        return 0;
    }

    private int getSelectedChildCount() {
        for (JRadioButton rb : childCountButtons) {
            if (rb.isSelected()) {
                return Integer.parseInt(rb.getText());
            }
        }
        return 0;
    }

    private void updateSummary(String movieTitle) {
        String selectedSeatsString = getSelectedSeatsString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String selectedDate = (dateChooser.getDate() != null) ? dateFormat.format(dateChooser.getDate()) : "N/A";
        String selectedTime = getSelectedTime();
        int totalAmount = calculateTotalAmount();

        summaryLabel.setText(String.format("<html>Movie: %s<br>Date: %s<br>Time: %s<br>Seats: %s<br>Total: %d 원</html>",
                movieTitle, selectedDate, selectedTime, selectedSeatsString, totalAmount));
    }

    private int calculateTotalAmount() {
        int adultCount = getSelectedAdultCount();
        int childCount = getSelectedChildCount();
        int totalAmount = (adultCount * 10000) + (childCount * 8000);
        return totalAmount;
    }

    private void updateSeatCount() {
        int selectedCount = selectedSeats.size();
        seatCountLabel.setText(String.format("Selected Seats: %d / 255", selectedCount));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BookingWindow("movie name", "/mnt/data/image.png").setVisible(true);
        });
    }
}
