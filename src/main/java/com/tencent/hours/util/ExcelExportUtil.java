package com.tencent.hours.util;

import lombok.extern.slf4j.Slf4j;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.Map;

/**
 * @author Gaohj
 * @package com.tencent.hours.util
 * @Description
 * @date 2020/11/24 11:02
 */
@Slf4j
public class ExcelExportUtil {

    public static File exportToFile(InputStream is, Map<String, Object> modelMap, File targetFile) {
        XLSTransformer xlsTransformer = new XLSTransformer();
        try {
            Workbook workbook = xlsTransformer.transformXLS(is, modelMap);
            OutputStream os = new BufferedOutputStream(new FileOutputStream(targetFile));
            workbook.write(os);
            is.close();
            os.flush();
            os.close();
            return targetFile;
        } catch (Exception e) {
            log.error("excel export failed:{}", e);
            throw new RuntimeException("excel导出失败：{}", e);
        }
    }
}
