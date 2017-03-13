package my.example.pdf;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author Duc Nguyen <duc.nguyen@axonactive.vn>
 */
public class Sign implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;//unique ID
    private String type;//ChamberSign || SuisseId || etc  (don't need to create parameterValues in V0)
    private String login;
    private String comment;
    private Integer signStatus;//null, Signed, Refused
    private Date modificationDate;//Modification date
    private Integer groupOrder;
    private Integer width;
    private Integer height;
    private Integer zoom;
    private Integer x;
    private Integer y;
    private Integer pageNumber;
    private String pageScale;
    private String fontScale;
    private String ratio;
    private Integer currentPageWidth;
    private Integer currentPageHeight;
    private Integer originalPageHeight;
    private Integer originalPageWidth;

    public Sign( Integer width, Integer height, Integer zoom, Integer x, Integer y, Integer pageNumber, String pageScale, String fontScale, String ratio, Integer currentPageWidth, Integer currentPageHeight, Integer originalPageHeight, Integer originalPageWidth) {
        this.width = width;
        this.height = height;
        this.zoom = zoom;
        this.x = x;
        this.y = y;
        this.pageNumber = pageNumber;
        this.pageScale = pageScale;
        this.fontScale = fontScale;
        this.ratio = ratio;
        this.currentPageWidth = currentPageWidth;
        this.currentPageHeight = currentPageHeight;
        this.originalPageHeight = originalPageHeight;
        this.originalPageWidth = originalPageWidth;
    }

    public static final Comparator<Sign> MODIFIED_DATE_COMPARATOR = new Comparator<Sign>() {
        @Override
        public int compare(Sign o1, Sign o2) {
            if (o1 != null && o1.getModificationDate() == null) {
                return -1;
            }
            if (o2 != null && o2.getModificationDate() == null) {
                return 1;
            }
            if (o1 != null && o2 != null) {
                return o1.getModificationDate().compareTo(o2.getModificationDate());
            }
            if (o1 == null && o2 == null) {
                return 0;
            }
            return -1;

        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(Integer signStatus) {
        this.signStatus = signStatus;
    }

    public Integer getGroupOrder() {
        return groupOrder;
    }

    public void setGroupOrder(Integer groupOrder) {
        this.groupOrder = groupOrder;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getZoom() {
        return zoom;
    }

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageScale() {
        return pageScale;
    }

    public void setPageScale(String pageScale) {
        this.pageScale = pageScale;
    }

    public String getFontScale() {
        return fontScale;
    }

    public void setFontScale(String fontScale) {
        this.fontScale = fontScale;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public Integer getCurrentPageWidth() {
        return currentPageWidth;
    }

    public void setCurrentPageWidth(Integer currentPageWidth) {
        this.currentPageWidth = currentPageWidth;
    }

    public Integer getCurrentPageHeight() {
        return currentPageHeight;
    }

    public void setCurrentPageHeight(Integer currentPageHeight) {
        this.currentPageHeight = currentPageHeight;
    }

    public Integer getOriginalPageHeight() {
        return originalPageHeight;
    }

    public void setOriginalPageHeight(Integer originalPageHeight) {
        this.originalPageHeight = originalPageHeight;
    }

    public Integer getOriginalPageWidth() {
        return originalPageWidth;
    }

    public void setOriginalPageWidth(Integer originalPageWidth) {
        this.originalPageWidth = originalPageWidth;
    }

    /**
     * @return the modificationDate
     */
    public Date getModificationDate() {
        if (this.modificationDate != null) {
            return new Date(this.modificationDate.getTime());
        }
        return null;
    }

    /**
     * @param modificationDate the modificationDate to set
     */
    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate != null ? new Date(modificationDate.getTime()) : null;
    }

}
