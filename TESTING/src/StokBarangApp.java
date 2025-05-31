package aplikasipembukuanbarang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;

public class StokBarangApp extends JFrame {
    private JTextField namaField, jumlahField, hargaField;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel totalHargaLabel;
    private int totalHarga = 0;

    public StokBarangApp() {
        setTitle("Aplikasi Stok Barang");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(200, 255, 200));

        JLabel header = new JLabel("HAIII ENJOYY", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 28));

        namaField = new JTextField(12);
        jumlahField = new JTextField(5);
        hargaField = new JTextField(7);

        JButton tambahButton = new JButton("Tambah");
        JButton kurangiButton = new JButton("Kurangi");

        // ENTER navigation
        namaField.addActionListener(e -> jumlahField.requestFocus());
        jumlahField.addActionListener(e -> hargaField.requestFocus());
        hargaField.addActionListener(e -> {
            tambahBarang();
            namaField.requestFocus();
        });

        tambahButton.addActionListener(e -> {
            tambahBarang();
            namaField.requestFocus();
        });

        kurangiButton.addActionListener(e -> kurangiBarang());

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.setBackground(new Color(200, 255, 200));
        inputPanel.add(new JLabel("Nama:")); inputPanel.add(namaField);
        inputPanel.add(new JLabel("Jumlah:")); inputPanel.add(jumlahField);
        inputPanel.add(new JLabel("Harga:")); inputPanel.add(hargaField);
        inputPanel.add(tambahButton);
        inputPanel.add(kurangiButton);

        // Tabel
        tableModel = new DefaultTableModel(new Object[]{"Nama", "Jumlah", "Harga", "Total"}, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(18);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(table);

        // Total Harga
        totalHargaLabel = new JLabel("Total Harga: 0");
        totalHargaLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalPanel.setBackground(new Color(200, 255, 200));
        totalPanel.add(totalHargaLabel);

        // Tombol Simpan
        JButton simpanButton = new JButton("Simpan ke File");
        simpanButton.addActionListener(e -> simpanKeFile());

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(200, 255, 200));
        bottomPanel.add(totalPanel, BorderLayout.WEST);
        bottomPanel.add(simpanButton, BorderLayout.EAST);

        // Layout utama
        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void tambahBarang() {
        try {
            String namaInput = namaField.getText().trim();
            if (namaInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama barang tidak boleh kosong.");
                return;
            }

            // Kapitalisasi huruf pertama
            String nama = namaInput.substring(0, 1).toUpperCase() + namaInput.substring(1).toLowerCase();

            int jumlah = Integer.parseInt(jumlahField.getText().trim());
            int harga = Integer.parseInt(hargaField.getText().trim());
            int total = jumlah * harga;

            tableModel.addRow(new Object[]{nama, jumlah, harga, total});
            totalHarga += total;
            updateTotals();

            namaField.setText("");
            jumlahField.setText("");
            hargaField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah dan Harga harus angka.");
        }
    }

    private void kurangiBarang() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int total = (int) tableModel.getValueAt(selectedRow, 3);
            totalHarga -= total;
            tableModel.removeRow(selectedRow);
            updateTotals();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih baris yang akan dihapus.");
        }
    }

    private void updateTotals() {
        totalHargaLabel.setText("Total Harga: " + totalHarga);
    }

    private void simpanKeFile() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Simpan sebagai .txt");

        int option = fc.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            try (FileWriter writer = new FileWriter(fc.getSelectedFile() + ".txt")) {
                writer.write("Daftar Barang:\n");
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    writer.write(
                            tableModel.getValueAt(i, 0) + " | " +
                                    tableModel.getValueAt(i, 1) + " | " +
                                    tableModel.getValueAt(i, 2) + " | " +
                                    tableModel.getValueAt(i, 3) + "\n"
                    );
                }
                writer.write("\nTotal Harga: " + totalHarga + "\n");

                JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StokBarangApp::new);
    }
}
