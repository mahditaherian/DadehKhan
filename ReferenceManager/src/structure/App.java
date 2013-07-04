package structure;

import org.jsoup.nodes.Document;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class App extends JFrame {
    //    public static String htmlStr = "<html>\n" +
//            "<head><title>Example\n" +
//            "</title></head>\n" +
//            "<body>\n" +
//            "<p>It's a JEditorPane with HTML. It could be plain text. Also the paragraph may contain attributed text e.g. <b>bold</b> or <i>italic<i>. </p>\n" +
//            "<table align=\"left\" width=\"200px\" border='1'>\n" +
//            "  <tr><td>inner 1:1</td><td>inner 1:2</td></tr>\n" +
//            "  <tr><td>inner 2:1</td><td>inner 2:2</td></tr>\n" +
//            "  <tr><td>inner 3:1</td><td>inner 3:2</td></tr>\n" +
//            " </table>\n" +
//            "</body>\n" +
//            "</html>\n";
    JEditorPane edit = new JEditorPane();
    JTextArea editSrc = new JTextArea();
    EditorPaneStructure info;

    public static void main(String[] args) {
        final App app = new App();

        app.setSize(750, 550);
        app.setLocationRelativeTo(null);
        app.setExtendedState(JFrame.MAXIMIZED_BOTH);
        app.setVisible(true);

        final Timer t = new Timer(300, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ((Timer) e.getSource()).stop();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        app.info.refresh();
                    }
                });
            }
        });

        t.start();
    }

    public App() {
        super("Editor pane structures example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        edit.setEditorKit(new HTMLEditorKit());
        edit.setContentType("text/html");
        {

            Document doc = null;


            try {
//                doc = Jsoup.parse(new URL("http://www.ghatreh.com/news/khodro.php"), 5000);
                File file = new File("data/html/1.html");
                InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
//                    stringBuilder.append("\r\n");
                }

                htmlStr = stringBuilder.toString();
//                htmlStr = doc.outerHtml();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        htmlStr = htmlStr.replaceAll("\n", "");
        htmlStr = htmlStr.replaceAll("\r", "");
        htmlStr = htmlStr.replaceAll("\t", "");
//        try {
//            Reader stringReader = new StringReader(htmlStr);
//            HTMLEditorKit htmlKit = new HTMLEditorKit();
//            HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
//            HTMLEditorKit.Parser parser = new ParserDelegator();
//            parser.parse(stringReader, htmlDoc.getReader(0), true);
//            edit.setDocument(htmlDoc);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        edit.setText(htmlStr);

        JScrollPane scroll = new JScrollPane(edit);
        final JTabbedPane tbPane = new JTabbedPane();
        tbPane.add("HTML", scroll);
        scroll = new JScrollPane(editSrc);
        editSrc.setText(htmlStr);
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.add(scroll, BorderLayout.CENTER);
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnShow = new JButton("Show HTML");
        btnShow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                edit.setText(editSrc.getText());
                tbPane.setSelectedIndex(0);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        info.refresh();
                    }
                });
            }
        });
        pnlButtons.add(btnShow);
        pnl.add(pnlButtons, BorderLayout.SOUTH);
        tbPane.add("Source", pnl);

        info = new EditorPaneStructure(edit, editSrc);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tbPane, new JScrollPane(info));
        split.setDividerLocation(900);
        getContentPane().add(split);
    }

    public static String htmlStr = "";
    public static String htmlStr2 = "<table id=\"tbl_id\" align=center width=90% border=1>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center><strong>توليد كننده</strong></td>\n" +
            "\t\t<td align=center><strong>نوع خودرو</strong></td>\n" +
            "\t\t<td align=center><strong>قيمت كارخانه</strong></td>\n" +
            "\t\t<td align=center><strong>قيمت بازار</strong></td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>ايران خودرو</td>\n" +
            "\t\t<td align=center>پژو  206 تيپ2</td>\n" +
            "\t\t<td align=center>29</td>\n" +
            "\t\t<td align=center>33</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>ايران خودرو</td>\n" +
            "\t\t<td align=center>پژو 206 تيپ 5</td>\n" +
            "\t\t<td align=center>34</td>\n" +
            "\t\t<td align=center>38</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>ايران خودرو</td>\n" +
            "\t\t<td align=center>پژو 206 تيپ 6</td>\n" +
            "\t\t<td align=center>39</td>\n" +
            "\t\t<td align=center>51</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>ايران خودرو</td>\n" +
            "\t\t<td align=center>پژو 206 صندوقدار مدل  v8</td>\n" +
            "\t\t<td align=center>34.5</td>\n" +
            "\t\t<td align=center>41</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>ايران خودرو</td>\n" +
            "\t\t<td align=center>پژو 206 صندوقدار مدل  v9</td>\n" +
            "\t\t<td align=center>39.5</td>\n" +
            "\t\t<td align=center>52</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>ايران خودرو</td>\n" +
            "\t\t<td align=center>پژو 207 اتوماتيك</td>\n" +
            "\t\t<td align=center>45.5</td>\n" +
            "\t\t<td align=center>56</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>ايران خودرو</td>\n" +
            "\t\t<td align=center>پژو 207 معمولي</td>\n" +
            "\t\t<td align=center>38.5</td>\n" +
            "\t\t<td align=center>51</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>ايران خودرو</td>\n" +
            "\t\t<td align=center>پژو 405 بنزيني</td>\n" +
            "\t\t<td align=center>22.18</td>\n" +
            "\t\t<td align=center>23.5</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>ايران خودرو</td>\n" +
            "\t\t<td align=center>پژو پارس سال</td>\n" +
            "\t\t<td align=center>26.97</td>\n" +
            "\t\t<td align=center>32</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>ايران خودرو</td>\n" +
            "\t\t<td align=center>تندر 90 مدل E1</td>\n" +
            "\t\t<td align=center>31.55</td>\n" +
            "\t\t<td align=center>29</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>ايران خودرو</td>\n" +
            "\t\t<td align=center>تندر 90 مدل E2</td>\n" +
            "\t\t<td align=center>35.05</td>\n" +
            "\t\t<td align=center>30</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>ايران خودرو</td>\n" +
            "\t\t<td align=center>رانا</td>\n" +
            "\t\t<td align=center>30.5</td>\n" +
            "\t\t<td align=center>31</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>ايران خودرو</td>\n" +
            "\t\t<td align=center>سمند ال ايكس سال</td>\n" +
            "\t\t<td align=center>23</td>\n" +
            "\t\t<td align=center>23.5</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>ايران خودرو</td>\n" +
            "\t\t<td align=center>سوزوكي ويتارا اتوماتيك</td>\n" +
            "\t\t<td align=center>58.274</td>\n" +
            "\t\t<td align=center>11.9</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>تويوتا</td>\n" +
            "\t\t<td align=center>كرولا</td>\n" +
            "\t\t<td align=center>56</td>\n" +
            "\t\t<td align=center>100</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>تويوتا</td>\n" +
            "\t\t<td align=center>ياريس هاچ‌بك</td>\n" +
            "\t\t<td align=center>46</td>\n" +
            "\t\t<td align=center>76</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>ساپپا</td>\n" +
            "\t\t<td align=center>تيبا</td>\n" +
            "\t\t<td align=center>18.79</td>\n" +
            "\t\t<td align=center>19</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>سايپا</td>\n" +
            "\t\t<td align=center>111-SX</td>\n" +
            "\t\t<td align=center>15.268</td>\n" +
            "\t\t<td align=center>16.5</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>سايپا</td>\n" +
            "\t\t<td align=center>131 -SX</td>\n" +
            "\t\t<td align=center>14.967</td>\n" +
            "\t\t<td align=center>16</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>سايپا</td>\n" +
            "\t\t<td align=center>132-SX</td>\n" +
            "\t\t<td align=center>15.082</td>\n" +
            "\t\t<td align=center>16.4</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>سايپا</td>\n" +
            "\t\t<td align=center>تندر 90 مدل E2</td>\n" +
            "\t\t<td align=center>35.05</td>\n" +
            "\t\t<td align=center>31</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>سايپا</td>\n" +
            "\t\t<td align=center>تيانا High</td>\n" +
            "\t\t<td align=center>134.3</td>\n" +
            "\t\t<td align=center>126</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>سايپا</td>\n" +
            "\t\t<td align=center>ماكسيما اتوماتيك</td>\n" +
            "\t\t<td align=center>103</td>\n" +
            "\t\t<td align=center>11</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>سايپا</td>\n" +
            "\t\t<td align=center>مگان اتوماتيك 2000 سي‌سي</td>\n" +
            "\t\t<td align=center>74.4</td>\n" +
            "\t\t<td align=center>77</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>كيا</td>\n" +
            "\t\t<td align=center>اپتيما</td>\n" +
            "\t\t<td align=center>132.6</td>\n" +
            "\t\t<td align=center>142</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>كيا</td>\n" +
            "\t\t<td align=center>اسپورتيج 2400</td>\n" +
            "\t\t<td align=center>126.7</td>\n" +
            "\t\t<td align=center>153</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>كيا</td>\n" +
            "\t\t<td align=center>سراتو 2000</td>\n" +
            "\t\t<td align=center>95</td>\n" +
            "\t\t<td align=center>110</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>كيا</td>\n" +
            "\t\t<td align=center>سراتو كوپه 2000</td>\n" +
            "\t\t<td align=center>93</td>\n" +
            "\t\t<td align=center>102</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>كيا</td>\n" +
            "\t\t<td align=center>موهاوي</td>\n" +
            "\t\t<td align=center>177.4</td>\n" +
            "\t\t<td align=center>185</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>مزدا</td>\n" +
            "\t\t<td align=center>مزدا 2 تيپ 2</td>\n" +
            "\t\t<td align=center>69</td>\n" +
            "\t\t<td align=center>75</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>مزدا</td>\n" +
            "\t\t<td align=center>مزدا 3 با موتور 2000 سي‌سي</td>\n" +
            "\t\t<td align=center>95</td>\n" +
            "\t\t<td align=center>118</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>هيوندايي</td>\n" +
            "\t\t<td align=center>I20</td>\n" +
            "\t\t<td align=center>49.301</td>\n" +
            "\t\t<td align=center>65</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>هيوندايي</td>\n" +
            "\t\t<td align=center>I30</td>\n" +
            "\t\t<td align=center>62.642</td>\n" +
            "\t\t<td align=center>95</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>هيوندايي</td>\n" +
            "\t\t<td align=center>آزرا گرانجور</td>\n" +
            "\t\t<td align=center>156</td>\n" +
            "\t\t<td align=center>185</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>هيوندايي</td>\n" +
            "\t\t<td align=center>جنسيس</td>\n" +
            "\t\t<td align=center>199</td>\n" +
            "\t\t<td align=center>260</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>هيوندايي</td>\n" +
            "\t\t<td align=center>سانتافه</td>\n" +
            "\t\t<td align=center>97.379</td>\n" +
            "\t\t<td align=center>170</td></tr>\n" +
            "\t<tr height=25>\n" +
            "\t\t<td align=center>هيوندايي</td>\n" +
            "\t\t<td align=center>سوناتا</td>\n" +
            "\t\t<td align=center>130</td>\n" +
            "\t\t<td align=center>150</td>\n" +
            "\t</tr>\n" +
            "</table>";
}
