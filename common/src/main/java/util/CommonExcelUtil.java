//package util;
//
//
//import com.le.jr.trade.publictools.page.Page;
//import com.le.jr.trade.publictools.util.JsonUtils;
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.CreationHelper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.lang.reflect.Field;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * Created by zhangshouzheng on 2016/6/28.
// */
//public class CommonExcelUtil {
//    private static final Logger LOGGER = LoggerFactory.getLogger(CommonExcelUtil.class);
//    //wrap 内容操作的字符大小界限
//    private static final int wrapCharSize=50;
//    //每个sheet页承载数据量
//    private static final int perSheetSize=60000;
//    //excel最大sheet数量
//    private static final int maxSheetSize=10;
//    //服务器暂存文件夹名称
//    private static final String downloadTmp="downloadTmp";
//
//    public static CommonExcelUtil getInstance() {
//        return new CommonExcelUtil();
//    }
//
//    /**
//     * 导出excel公用方法,直接返回到response输出流
//     * @param headKeys  excel头部信息，Map结构，key为List<T>中T的属性名 value为中文title
//     * @param fileNameSuffix
//     * @param list
//     * @param response
//     * @param <T>
//     * @throws IOException
//     */
//    public static <T> void exportExcel(Map<String, String> headKeys, String fileNameSuffix, List<T> list, HttpServletResponse response) throws IOException {
//        String fileName = fileNameSuffix + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        response.reset();
//        response.setContentType("application/vnd.ms-excel;charset=utf-8");
//        response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
//        OutputStream outputStream = null;
//        try {
//            outputStream = response.getOutputStream();
//            createWorkBook(list, headKeys).write(outputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (outputStream != null) {
//                outputStream.close();
//            }
//        }
//    }
//
//    /**
//     *  导出excel公用入口
//     * @param headKeys        excel头部，key对应Page<T>中的T对象的属性名
//     * @param fileNameSuffix 导出excel名称前缀
//     * @param page            导出excel分页参数
//     * @param request
//     * @param response
//     * @param <T>
//     */
//    public <T> void commonEntry(Map<String, String> headKeys, String fileNameSuffix, Page<T> page, HttpServletRequest request, HttpServletResponse response) {
//        int totalCount = page.getTotalCount();//总数据量
//        int currentPageNo = page.getCurrentPageNo();//当前页
//        int totalPageCount = page.getTotalPageCount();//总页数
//        try {
//            if(currentPageNo == 0){
//                throw new Exception("参数传递错误。currentPageNo="+currentPageNo);
//            }
//            //一页数据，直接导出下载
//            if (currentPageNo == 1 && totalPageCount == 1 && totalCount <= perSheetSize) {
//                exportExcel(headKeys, fileNameSuffix, page.getDataList(), response);
//                return;
//            }
//            //单excel多页数据，批次插入时
//            if (totalCount <= perSheetSize * maxSheetSize) {
//                //单excel,多sheet页处理方式
//                String excelFileName = resolveExcelName(fileNameSuffix, page);
//                if (excelFileName == null) {
//                    LOGGER.info("导出excel失败，获取finaName失败。");
//                    return;
//                }
//                //导出表头
//                if(currentPageNo == 1){
//                    exportExcelForPageInit(headKeys, excelFileName, request, response);
//                }
//                if (totalPageCount > 1) {
//                    //导出数据
//                    exportExcelForPageAppend(headKeys, excelFileName, page, request, response);
//                    //最后一页数据时，读取excel文件下载
//                    if(currentPageNo == totalPageCount) {
//                        downloadFile(excelFileName, request, response);
//                    }
//                }else{
//                    //无数据，读取excel文件下载表头
//                    downloadFile(excelFileName, request, response);
//                }
//            } else {
//                //总量超过65万条，分excel，压缩处理后下载
//            }
//        } catch (FileNotFoundException e){
//            LOGGER.error("找不到需要下载的文件！", e);
//        } catch (Exception e){
//            LOGGER.error("文件下载异常！", e);
//        }
//    }
//
//    /**
//     * download file下载指定文件方法
//     * @param excelFileName
//     * @param request
//     * @param response
//     */
//    private void downloadFile(String excelFileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        String realPath = request.getSession().getServletContext().getRealPath("/") +
//                downloadTmp +File.separator+excelFileName;
//        File targetFile = new File(realPath);
//        InputStream is = new BufferedInputStream(new FileInputStream(targetFile));
//        response.reset();
//        // 设置http头InputStream
//        // response.setContentType("application/vnd.ms-excel;charset=utf-8");
//        response.setContentType("application/octet-stream");
//        response.setHeader("Content-Disposition", "attachment;filename=" + new String(excelFileName.getBytes(), "iso-8859-1"));
//        // 发送输出流到Response
//        OutputStream os = new BufferedOutputStream(response.getOutputStream());
//        byte[] buf = new byte[2048];
//        int len;
//        while ((len = is.read(buf)) != -1) {
//            os.write(buf, 0, len);
//        }
//        os.flush();
//        is.close();
//        os.close();
//    }
//
//    /**
//     * 追加excel方法
//     * @param headKeys
//     * @param excelFileName
//     * @param page
//     * @param request
//     * @param response
//     * @param <T>
//     */
//    private <T> void exportExcelForPageAppend(Map<String, String> headKeys, String excelFileName, Page<T> page, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        //step 1.获取当前所处excel名
//        String realPath = request.getSession().getServletContext().getRealPath("/") + downloadTmp;
//        File targetFile = new File(realPath);
//
//        FileInputStream fs = new FileInputStream(targetFile+File.separator+excelFileName);
//        //step 2.获取当前数据应该追加到excel所处的sheet单元
//        int sheetNo = resolveSheetNo(page);
//        if(sheetNo<0){
//            throw new Exception("获取sheetNo错误。参数page:"+ JsonUtils.writeValue(page));
//        }
//        POIFSFileSystem ps = new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息
//        HSSFWorkbook wb = new HSSFWorkbook(ps);
//        //HSSFSheet sheet = wb.getSheetAt(sheetNo);  //获取到工作表，因为一个excel可能有多个工作表
//
//        FileOutputStream out = new FileOutputStream(targetFile+File.separator+excelFileName);
//        //TODO FIX IT
//        wb = appendSheet(headKeys,page.getDataList(),wb,sheetNo);
//
//
//        out.flush();
//        wb.write(out);
//        fs.close();
//        out.close();
//    }
//
//    /**
//     * append数据到sheet页
//     * @param headKeys
//     * @param dataList
//     * @param wb
//     * @param sheetNo
//     * @param <T>
//     * @return
//     */
//    private <T> HSSFWorkbook appendSheet(Map<String, String> headKeys,
//                                      List<T> dataList,HSSFWorkbook wb,int sheetNo) throws Exception {
//        int existSheetNum= wb.getNumberOfSheets();
//        boolean newSheetFlag=false;
//        //判断sheet是否存在
//        if((sheetNo-1) == existSheetNum){
//            newSheetFlag =true;
//        }
//        //不存在，并且前一个sheet NO存在，则新建sheet
//        if(newSheetFlag){
//            wb.createSheet("sheet"+sheetNo);
//        }
//        HSSFSheet sheet = wb.getSheetAt(sheetNo-1);  //获取到工作表，因为一个excel可能有多个工作表
//        Set<String> sets = headKeys.keySet();
//        int len= sets.size();
//        // 提取vo属性名
//        String[] columns = new String[len];
//        for (int i = 0; i < len; i++) {
//            columns[i] = sets.toArray()[i].toString();
//        }
//        //定义style
//        HSSFCellStyle style = getStyle(wb,wb.createCellStyle());
//        //设置每行每列的值
//        HSSFRow row = sheet.getRow(0);
//        int lastRowNo = sheet.getLastRowNum();
//        int startPos=1;
//        int length=dataList.size();
//        //处理新建sheet时，第一行数据空问题
//        if(newSheetFlag){
//            startPos=0;
//            length=dataList.size()-1;
//        }
//
//        for (int m = startPos; m <= length; m++) {
//            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
//            // 创建一行，在页sheet上
//            row = sheet.createRow((short) (lastRowNo + m)); //在现有行号后追加数据
//            // 在row行上创建一个方格
//            Object o = null;
//            if(newSheetFlag){
//                o = dataList.get(m);
//            }else{
//                o = dataList.get(m-1);
//            }
//            if (o == null) {
//                continue;
//            }
//            for(int n=0;n<len;n++){
//                HSSFCell cell = row.createCell(n,HSSFCell.CELL_TYPE_STRING);
//                cell.setCellValue(getValue(o, columns[n]));
//
//            }
//        }
//        //自动调整宽度，设置wrap
//        if(newSheetFlag) {
//            autoAdapt(sheet,wb,len,style);
//        }
//
//        return  wb;
//    }
//
//    /**
//     *  多页数据需要插入excel sheet页时，第一页数据插入，创建excel，sheet，插入数据
//     * @param headKeys
//     * @param excelFileName
//     * @param request
//     * @param response
//     * @param <T>
//     */
//    private <T> void exportExcelForPageInit(Map<String, String> headKeys, String excelFileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        FileOutputStream outputStream = null;
//        try {
//            String realPath = request.getSession().getServletContext().getRealPath("/") + downloadTmp;
//            File targetFile = new File(realPath);
//            if(!targetFile.exists()){
//                targetFile.mkdirs();
//            }else{
//                this.deleteDir(targetFile);
//                targetFile.mkdirs();
//            }
//            outputStream = new FileOutputStream(targetFile+File.separator+excelFileName);
//
//            this.createWorkBookHead(headKeys).write(outputStream);
//        } catch (IOException e) {
//            LOGGER.info("导出excel文件异常，文件={}", excelFileName);
//            throw new RuntimeException("导出excel文件异常");
//        } finally {
//            if (outputStream != null) {
//                outputStream.close();
//            }
//        }
//    }
//
//
//    /**
//     * 获取excel命名字符串
//     * @param page
//     * @param <T>
//     * @return
//     */
//    private <T> String resolveExcelName(String fileNameSuffix,Page<T> page) {
//        String excelName="";
//        //获取excel个数
//        int excelCount = 1;
//        int oneExcelCount = perSheetSize*maxSheetSize;
//        excelCount = page.getTotalCount()/oneExcelCount+page.getTotalCount()%oneExcelCount>0?1:0;
//        //获取当前传送数据所处的excel名称
//        int insertedData= page.getCurrentPageNo()*page.getPageSize();
//        if(excelCount == 1) {
//            excelName = fileNameSuffix + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".xls";
//        }else{
//            int partNo = (insertedData % oneExcelCount) > 0 ? (1+insertedData / oneExcelCount) : (insertedData / oneExcelCount);
//            excelName = fileNameSuffix + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "part" + partNo + ".xls";
//        }
//        return excelName;
//    }
//
//    /**
//     * 获取数据所处的sheetNo号
//     * @param page
//     * @param <T>
//     * @return
//     */
//    private <T> int resolveSheetNo(Page<T> page) {
//        int sheetNo = 1;
//        int insertedData= page.getCurrentPageNo()*page.getPageSize();
//        sheetNo = insertedData%perSheetSize>0?(1+insertedData/perSheetSize):(insertedData/perSheetSize);
//        if (sheetNo == 0){
//            return -1;
//        }
//        if(sheetNo%maxSheetSize == 0){//整除
//            sheetNo = 10;
//        }else{
//            sheetNo = sheetNo%maxSheetSize;
//        }
//        return sheetNo;
//    }
//
//    public static <T> HSSFWorkbook createWorkBook(List<T> dataList, Map<String, String> headKeys) {
//        // 创建excel工作簿
//        HSSFWorkbook wb = new HSSFWorkbook();
//        // 创建第一个sheet（页），并命名
//        HSSFSheet sheet = wb.createSheet("sheet1");
//
//        CreationHelper createHelper = wb.getCreationHelper();
//        Set<String> sets = headKeys.keySet();
//        int len= sets.size();
//        // 提取vo属性名
//        String[] columns = new String[len];
//        for (int i = 0; i < len; i++) {
//            columns[i] = sets.toArray()[i].toString();
//        }
//        //头部，第一行
//        HSSFRow row = sheet.createRow(0);
//        int i=0;
//        //定义style
//        HSSFCellStyle style = getColumnTopStyle(wb,wb.createCellStyle());
//        // 定义表头
//        for (Iterator<String> it = sets.iterator(); it.hasNext();) {
//            String key = it.next();
//            HSSFCell cell = row.createCell(i++);
//            cell.setCellValue(createHelper.createRichTextString(headKeys.get(key)));
//            cell.setCellStyle(style);
//        }
//        style = getStyle(wb,wb.createCellStyle());
//        //设置每行每列的值
//        for (int m = 1, length = dataList.size(); m <= length; m++) {
//            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
//            // 创建一行，在页sheet上
//            HSSFRow row1 = sheet.createRow(m);
//            // 在row行上创建一个方格
//            Object o = dataList.get(m-1);
//            if (o == null) {
//                continue;
//            }
//            for(int n=0;n<len;n++){
//                HSSFCell cell = row1.createCell(n,HSSFCell.CELL_TYPE_STRING);
//                cell.setCellValue(getValue(o, columns[n]));
//
//            }
//        }
//        //自动调整宽度，设置wrap
//        autoAdapt(sheet,wb,len,style);
//
//        return  wb;
//    }
//
//    public static <T> HSSFWorkbook createWorkBookHead(Map<String, String> headKeys) {
//        // 创建excel工作簿
//        HSSFWorkbook wb = new HSSFWorkbook();
//        // 创建第一个sheet（页），并命名
//        HSSFSheet sheet = wb.createSheet("sheet1");
//
//        CreationHelper createHelper = wb.getCreationHelper();
//        Set<String> sets = headKeys.keySet();
//        int len= sets.size();
//        // 提取vo属性名
//        String[] columns = new String[len];
//        for (int i = 0; i < len; i++) {
//            columns[i] = sets.toArray()[i].toString();
//        }
//        //头部，第一行
//        HSSFRow row = sheet.createRow(0);
//        int i=0;
//        //定义style
//        HSSFCellStyle style = getColumnTopStyle(wb,wb.createCellStyle());
//        // 定义表头
//        for (Iterator<String> it = sets.iterator(); it.hasNext();) {
//            String key = it.next();
//            HSSFCell cell = row.createCell(i++);
//            cell.setCellValue(createHelper.createRichTextString(headKeys.get(key)));
//            cell.setCellStyle(style);
//        }
//        style = getStyle(wb,wb.createCellStyle());
//        //自动调整宽度，设置wrap
//        autoAdapt(sheet,wb,len,style);
//        return  wb;
//    }
//    /**
//     * 获取对应的值
//     * @param o
//     * @param fieldName
//     * @return
//     */
//    private static String getValue(Object o, String fieldName) {
//        Object o1 = null;
//        try {
//            Class c = o.getClass();
//            Field field = c.getDeclaredField(fieldName);
//            field.setAccessible(true);
//            o1 = field.get(o);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return o1==null?"":o1.toString();
//    }
//
//    /*
//    * 列数据信息单元格样式
//    */
//    public static HSSFCellStyle getStyle(HSSFWorkbook workbook,HSSFCellStyle style) {
//        // 设置字体
//        HSSFFont font = workbook.createFont();
//        //设置字体大小
//        //font.setFontHeightInPoints((short)10);
//        //字体加粗
//        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        //设置字体名字
//        font.setFontName("Courier New");
//        //设置底边框;
//        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        //设置底边框颜色;
//        style.setBottomBorderColor(HSSFColor.BLACK.index);
//        //设置左边框;
//        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        //设置左边框颜色;
//        style.setLeftBorderColor(HSSFColor.BLACK.index);
//        //设置右边框;
//        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        //设置右边框颜色;
//        style.setRightBorderColor(HSSFColor.BLACK.index);
//        //设置顶边框;
//        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        //设置顶边框颜色;
//        style.setTopBorderColor(HSSFColor.BLACK.index);
//        //在样式用应用设置的字体;
//        style.setFont(font);
//        //设置自动换行;
//        style.setWrapText(false);
//        //设置水平对齐的样式为居中对齐;
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        //设置垂直对齐的样式为居中对齐;
//        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        return style;
//    }
//
//    private static void autoAdapt(HSSFSheet sheet,HSSFWorkbook wb,int len,HSSFCellStyle style) {
//        //让列宽随着导出的列长自动适应
//        for (int colNum = 0; colNum < len; colNum++) {
//            int columnWidth = sheet.getColumnWidth(colNum) / 256;
//            for (int rowNum = 1; rowNum < sheet.getLastRowNum(); rowNum++) {
//                HSSFRow currentRow;
//                //当前行未被使用过
//                if (sheet.getRow(rowNum) == null) {
//                    currentRow = sheet.createRow(rowNum);
//                } else {
//                    currentRow = sheet.getRow(rowNum);
//                }
//                if (currentRow.getCell(colNum) != null) {
//                    HSSFCell currentCell = currentRow.getCell(colNum);
//                    if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
//                        int length = currentCell.getStringCellValue().getBytes().length;
//                        //长度大于wrapCharSize个字符的利用wrapText处理
//                        if(length < wrapCharSize){
//                            if (columnWidth < length){
//                                columnWidth = length;
//                            }
//                        }else{
//                            columnWidth = wrapCharSize;
//                            //设置内容wrap
//                            currentCell.setCellStyle(getWrapStyle(wb,style));
//                        }
//                    }
//                }
//            }
//            if(colNum == 0){
//                sheet.setColumnWidth(colNum, (columnWidth+4) * 256);
//            }else{
//                sheet.setColumnWidth(colNum, (columnWidth+4) * 256);
//            }
//        }
//    }
//
//    /*
//     * wrap单元格样式
//     */
//    public static HSSFCellStyle getWrapStyle(HSSFWorkbook workbook,HSSFCellStyle style) {
//        style.setWrapText(true);
//        style.setBorderLeft(CellStyle.BORDER_THIN);
//        style.setBorderRight(CellStyle.BORDER_THIN);
//        style.setBorderTop(CellStyle.BORDER_THIN);
//        style.setBorderBottom(CellStyle.BORDER_THIN);
//        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//
//        return style;
//    }
//    /*
//     * 列头单元格样式
//     */
//    public static HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook,HSSFCellStyle style) {
//
//        // 设置字体
//        HSSFFont font = workbook.createFont();
//        //设置字体大小
//        font.setFontHeightInPoints((short)11);
//        //字体加粗
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        //设置字体名字
//        font.setFontName("Courier New");
//        //设置样式;
////        HSSFCellStyle style = workbook.createCellStyle();
//        //设置底边框;
//        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        //设置底边框颜色;
//        style.setBottomBorderColor(HSSFColor.BLACK.index);
//        //设置左边框;
//        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        //设置左边框颜色;
//        style.setLeftBorderColor(HSSFColor.BLACK.index);
//        //设置右边框;
//        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        //设置右边框颜色;
//        style.setRightBorderColor(HSSFColor.BLACK.index);
//        //设置顶边框;
//        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        //设置顶边框颜色;
//        style.setTopBorderColor(HSSFColor.BLACK.index);
//        //在样式用应用设置的字体;
//        style.setFont(font);
//        //设置自动换行;
//        style.setWrapText(false);
//        //设置水平对齐的样式为居中对齐;
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        //设置垂直对齐的样式为居中对齐;
//        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//
//        return style;
//    }
//
//    /**
//     * 递归删除目录下的所有文件及子目录下所有文件
//     * @param dir 将要删除的文件目录
//     * @return boolean Returns "true" if all deletions were successful.
//     *                 If a deletion fails, the method stops attempting to
//     *                 delete and returns "false".
//     */
//    private static boolean deleteDir(File dir) {
//        if (dir.isDirectory()) {
//            String[] children = dir.list();
//            //递归删除目录中的子目录下
//            for (int i=0; i<children.length; i++) {
//                boolean success = deleteDir(new File(dir, children[i]));
//                if (!success) {
//                    return false;
//                }
//            }
//        }
//        // 目录此时为空，可以删除
//        return dir.delete();
//    }
//}
