
import javax.swing.*;
        import javax.swing.table.DefaultTableModel;
        import javax.swing.table.TableRowSorter;
        import java.io.BufferedReader;
        import java.io.FileReader;
        import java.io.IOException;

public class CsvToJTable {
    public static JTable getJTable(String fileName)  {

        String line;
        String cvsSplitBy = ","; // csv文件分隔符

        DefaultTableModel model = new DefaultTableModel();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // 读取csv文件的第一行作为表头
            if ((line = br.readLine()) != null) {
                String[] headers = line.split(cvsSplitBy);
                for (String header : headers) {
                    model.addColumn(header);
                }
            }

            // 读取csv文件的其余行作为数据行
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                model.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JTable table = new JTable(model);

        // 添加排序功能
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        return table;
    }
}