package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.Font;
import java.awt.Image;
import java.sql.*;

import db.DB1;

public class Register {

    private JFrame frame;
    private JTextField tfID;
    private JPanel startPage;
    private JPanel joinForm;
    private JTextField textField_3;
    private JLabel phoneNumberHintLabel;
    private int windowWidth = 450;
    private int windowHeight = 339;
    private JPasswordField passwordField;
    private JPasswordField passwordField_1;
    private JPasswordField pw;
    private JTextField textField_1;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Register window = new Register();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Register() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("로그인");
        frame.setBounds(100, 100, 450, 369);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // 로그인 패널 설정
        startPage = new JPanel();
        startPage.setBounds(0, 0, windowWidth, windowHeight);
        frame.getContentPane().add(startPage);
        startPage.setLayout(null);

        tfID = new JTextField();
        tfID.setBounds(131, 97, 225, 30);
        startPage.add(tfID);
        tfID.setColumns(10);

        JButton btnNext = new JButton("로그인");
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        btnNext.setBounds(60, 234, 130, 44);
        startPage.add(btnNext);

        JButton btnNewButton = new JButton("회원가입");
        btnNewButton.setBounds(226, 234, 130, 44);
        startPage.add(btnNewButton);

        JLabel lblNewLabel = new JLabel("ID");
        lblNewLabel.setFont(new Font("Bauhaus 93", Font.PLAIN, 12)); // 기본 폰트 사용
        lblNewLabel.setBounds(60, 97, 59, 22);
        startPage.add(lblNewLabel);

        JLabel lblPw = new JLabel("PW");
        lblPw.setFont(new Font("Arial Narrow", Font.PLAIN, 12)); // 기본 폰트 사용
        lblPw.setBounds(60, 160, 59, 22);
        startPage.add(lblPw);

        pw = new JPasswordField();
        pw.setEchoChar('*');
        pw.setBounds(131, 162, 225, 30);
        startPage.add(pw);

        // 이미지 로드 및 크기 조정
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\USER\\Downloads\\free-icon-movie-412887 (1).png");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(103, 77, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel lblNewLabel_1 = new JLabel(scaledIcon);
        lblNewLabel_1.setBounds(150, 10, 103, 77);
        startPage.add(lblNewLabel_1);

        // 회원가입 버튼 클릭 시 joinForm 패널을 보이도록 설정
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startPage.setVisible(false);
                joinForm.setVisible(true);
                frame.setTitle("회원가입"); // 타이틀 변경
            }
        });

        // 회원가입 패널 설정
        joinForm = new JPanel();
        joinForm.setBounds(0, 0, windowWidth, windowHeight);
        frame.getContentPane().add(joinForm);
        joinForm.setLayout(null);
        joinForm.setVisible(false);

        JLabel lblJoinId = new JLabel("ID");
        lblJoinId.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        lblJoinId.setBounds(22, 72, 100, 22);
        joinForm.add(lblJoinId);

        JTextField joinIdField = new JTextField();
        joinIdField.setBounds(99, 72, 225, 30);
        joinForm.add(joinIdField);

        JLabel lblJoinPw = new JLabel("PW");
        lblJoinPw.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        lblJoinPw.setBounds(22, 119, 100, 22);
        joinForm.add(lblJoinPw);

        JButton btnJoinBack = new JButton("회원가입");
        btnJoinBack.setBounds(177, 271, 130, 43);
        joinForm.add(btnJoinBack);

        btnJoinBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean isInputEmpty = joinIdField.getText().isEmpty()
                        || String.valueOf(passwordField.getPassword()).isEmpty()
                        || String.valueOf(passwordField_1.getPassword()).isEmpty() || textField_3.getText().isEmpty();
                if (isInputEmpty) {
                    JOptionPane.showMessageDialog(frame, "빈 칸이 있습니다.");
                } else if (!String.valueOf(passwordField.getPassword())
                        .equals(String.valueOf(passwordField_1.getPassword()))) {
                    JOptionPane.showMessageDialog(frame, "PW가 일치하지 않습니다.");
                } else {
                    register(joinIdField.getText(), String.valueOf(passwordField.getPassword()));
                }
            }
        });

        JLabel lblPw_1 = new JLabel("PW 확인");
        lblPw_1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        lblPw_1.setBounds(22, 169, 100, 22);
        joinForm.add(lblPw_1);

        textField_3 = new JTextField();
        textField_3.setBounds(99, 216, 225, 30);
        joinForm.add(textField_3);

        phoneNumberHintLabel = new JLabel("숫자만 입력 가능합니다.");
        phoneNumberHintLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        phoneNumberHintLabel.setBounds(99, 246, 225, 15);
        phoneNumberHintLabel.setVisible(false);
        joinForm.add(phoneNumberHintLabel);

        textField_3.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                phoneNumberHintLabel.setVisible(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                phoneNumberHintLabel.setVisible(false);
            }
        });

        JLabel lblJoinPw_1_1 = new JLabel("전화번호");
        lblJoinPw_1_1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        lblJoinPw_1_1.setBounds(22, 219, 100, 22);
        joinForm.add(lblJoinPw_1_1);

        JButton btnNewButton_1 = new JButton("중복체크");
        btnNewButton_1.setBounds(336, 71, 91, 30);
        joinForm.add(btnNewButton_1);

        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = joinIdField.getText(); // ID 입력란에서 입력된 텍스트 가져오기
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "아이디를 입력하세요.");
                } else if (checkDuplicateId(id)) {
                    JOptionPane.showMessageDialog(frame, "이미 사용 중인 ID입니다.");
                } else {
                    JOptionPane.showMessageDialog(frame, "사용 가능한 ID입니다.");
                }
            }
        });

        passwordField = new JPasswordField();
        passwordField.setEchoChar('*');
        passwordField.setBounds(99, 119, 225, 30);
        joinForm.add(passwordField);

        passwordField_1 = new JPasswordField();
        passwordField_1.setEchoChar('*');
        passwordField_1.setBounds(99, 169, 225, 30);
        joinForm.add(passwordField_1);

        JButton btnBack = new JButton("뒤로가기");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                joinForm.setVisible(false);
                startPage.setVisible(true);
                frame.setTitle("로그인"); // 타이틀 변경
            }
        });
        btnBack.setBounds(12, 277, 91, 30);
        joinForm.add(btnBack);

        JLabel lblJoinPw_1_1_1 = new JLabel("이름");
        lblJoinPw_1_1_1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        lblJoinPw_1_1_1.setBounds(22, 24, 100, 22);
        joinForm.add(lblJoinPw_1_1_1);

        textField_1 = new JTextField();
        textField_1.setBounds(99, 24, 225, 30);
        joinForm.add(textField_1);

        // DocumentFilter to allow only numeric input
        ((AbstractDocument) textField_3.getDocument()).setDocumentFilter(new NumericDocumentFilter());
    }

    private void login() {
        String id = tfID.getText();
        String password = new String(pw.getPassword());
        try (Connection conn = DB1.getConnection()) {
            String query = "SELECT * FROM users WHERE id = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(frame, "로그인 성공!");
            } else {
                JOptionPane.showMessageDialog(frame, "아이디 또는 비밀번호가 잘못되었습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void register(String id, String password) {
        try (Connection conn = DB1.getConnection()) {
            String query = "INSERT INTO users (id, password) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "회원가입 성공!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "회원가입 실패: " + e.getMessage());
        }
    }

    private boolean checkDuplicateId(String id) {
        try (Connection conn = DB1.getConnection()) {
            String query = "SELECT * FROM users WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // NumericDocumentFilter class to filter non-numeric input
    class NumericDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr)
                throws BadLocationException {
            if (string == null) {
                return;
            }
            if (string.matches("[0-9]+")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs)
                throws BadLocationException {
            if (text == null) {
                return;
            }
            if (text.matches("[0-9]+")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }
    }
}
