/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package kiemtra1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Kiemtra1 extends JFrame {
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private JButton sendImageButton;
    private JButton sendFileButton;
    private JButton createGroupButton;
    private JPanel imagePanel;
    private JPanel filePanel;
    private File selectedFile;

    public Kiemtra1() {
        setTitle("kiemtra1");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel chatPanel = new JPanel(new BorderLayout());
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        chatPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        messageField = new JTextField();
        sendButton = new JButton("Gửi tin nhắn");
        sendImageButton = new JButton("Gửi ảnh");
        sendFileButton = new JButton("Gửi file");
        createGroupButton = new JButton("Tạo nhóm");

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText();
                chatArea.append("Người dùng: " + message + "\n");
                messageField.setText("");
            }
        });

        sendImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    try {
                        byte[] fileData = Files.readAllBytes(selectedFile.toPath());
                        ImageIcon imageIcon = new ImageIcon(fileData);
                        Image scaledImage = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                        imagePanel.removeAll();
                        imagePanel.add(imageLabel);
                        imagePanel.revalidate();
                        imagePanel.repaint();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        chatArea.append("Lỗi khi đọc tệp ảnh\n");
                    }
                }
            }
        });

        sendFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    filePanel.removeAll();
                    JLabel fileLabel = new JLabel(selectedFile.getName());
                    filePanel.add(fileLabel);
                    filePanel.revalidate();
                    filePanel.repaint();
                }
            }
        });

        buttonPanel.add(messageField);
        buttonPanel.add(sendButton);
        buttonPanel.add(sendImageButton);
        buttonPanel.add(sendFileButton);
        buttonPanel.add(createGroupButton);

        imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        filePanel = new JPanel();
        filePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel previewPanel = new JPanel(new BorderLayout());
        previewPanel.add(new JLabel("Ảnh: "), BorderLayout.NORTH);
        previewPanel.add(imagePanel, BorderLayout.CENTER);

        JPanel downloadPanel = new JPanel(new BorderLayout());
        downloadPanel.add(new JLabel("Tệp tin: "), BorderLayout.NORTH);
        downloadPanel.add(filePanel, BorderLayout.CENTER);

        add(chatPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
        add(previewPanel, BorderLayout.WEST);
        add(downloadPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Kiemtra1();
    }
}