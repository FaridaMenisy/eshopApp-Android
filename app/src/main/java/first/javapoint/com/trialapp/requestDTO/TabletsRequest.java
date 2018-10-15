package first.javapoint.com.trialapp.requestDTO;

public class TabletsRequest{


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        private int id;
        private String tabletprice;
        private String tabletname;
        private String photo;
        private String color;
        private String installmentduration;

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    private String imagename;


        public String getTabletname() {
            return tabletname;
        }

        public void setTabletname(String tabletname) {
            this.tabletname = tabletname;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getInstallmentduration() {
            return installmentduration;
        }

        public void setInstallmentduration(String installmentduration) {
            this.installmentduration = installmentduration;
        }

        public String getInstallmentplan() {
            return installmentplan;
        }

        public void setInstallmentplan(String installmentplan) {
            this.installmentplan = installmentplan;
        }

        private String installmentplan;

        public String getTabletprice() {
            return tabletprice;
        }

        public void setTabletprice(String tabletprice) {
            this.tabletprice = tabletprice;
        }

        public String getCapacity() {
            return capacity;
        }

        public void setCapacity(String capacity) {
            this.capacity = capacity;
        }

        private String capacity;
}
