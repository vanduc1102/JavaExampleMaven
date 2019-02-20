package my.example.imagesignatory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import my.example.imagesignatory.utils.IOUtils;
import my.example.imagesignatory.utils.ImageUtil;

/** @author nvduc */
public class CreateImageSignatory {
  public static void main(String args[]) {}

  public String createSignImage(
      String signType,
      String backgroundPath,
      String reasonText,
      int lineLength,
      int fontSize,
      Boolean isIncludeText) {
    String textImagePath = null;
    String signImageFileOfUserPath = null;
    String backgroundImagePath = IOUtils.isFileExist(backgroundPath) ? backgroundPath : null;
    try {
      if (signImageFileOfUserPath == null) {
        if (backgroundImagePath == null) {
          if (isIncludeText) {
            return createImageReasonImage(reasonText, false, lineLength, fontSize);
          } else {
            return null;
          }
        } else {
          textImagePath = createImageReasonImage(reasonText, true, lineLength, fontSize);
          if (isIncludeText) {
            return ImageUtil.addBackgroundToImage(backgroundImagePath, textImagePath);
          } else {
            return backgroundImagePath;
          }
        }
      }
      textImagePath = createImageReasonImage(reasonText, true, lineLength, fontSize);
      if (backgroundImagePath == null) {
        if (isIncludeText) {
          return ImageUtil.mergeTwoImage(signImageFileOfUserPath, textImagePath, false);
        } else {
          return signImageFileOfUserPath;
        }
      } else {
        // imageMerged = ImageUtil.mergeTwoImage(signImageFileOfUserPath, textImagePath, true);
        if (isIncludeText) {
          return ImageUtil.createImageSignatory(
              backgroundImagePath, signImageFileOfUserPath, textImagePath);

        } else {
          return ImageUtil.addBackgroundToImage(backgroundImagePath, signImageFileOfUserPath);
        }
      }
    } catch (Exception ex) {
      //            log.debug("createSignImage ", ex);
    } finally {
      IOUtils.removeFile(textImagePath);
      IOUtils.removeFile(signImageFileOfUserPath);
    }
    return null;
  }

  private String createImageReasonImage(
      String text, boolean isNeededBackground, int lineLength, int fontSize) {
    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    Date today = Calendar.getInstance().getTime();
    String reportDate = df.format(today);
    String reasonText = text;
    reasonText += " ";
    reasonText = stringDivider(reasonText, lineLength);
    reasonText += "\n" + "Duc NGUYEN ";
    reasonText += "\nDate : " + reportDate;
    String textImage = ImageUtil.createImageFromText(reasonText, isNeededBackground, fontSize);
    return textImage;
  }

  private String stringDivider(String s, int lineLenght) {
    int length = (lineLenght == 0) ? 30 : lineLenght;
    StringBuilder sb = new StringBuilder(s);
    int i = 0;
    while ((i = sb.indexOf(" ", i + length)) != -1) {
      sb.replace(i, i + 1, "\n");
    }
    return sb.toString();
  }
}
