package sillyv.com.counterlists.screens.counters.recycler;

/**
 * Created by Vasili.Fedotov on 2/19/2017.
 *
 */

class CounterListModel {

    class CounterModel {

        private String name;
        private String amount;
        private String increment;
        private String decrement;

        public CounterModel() {
        }

        public CounterModel(String name, String amount, String increment, String decrement) {
            this.name = name;
            this.amount = amount;
            this.increment = increment;
            this.decrement = decrement;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getIncrement() {
            return increment;
        }

        public void setIncrement(String increment) {
            this.increment = increment;
        }

        public String getDecrement() {
            return decrement;
        }

        public void setDecrement(String decrement) {
            this.decrement = decrement;
        }
    }
}
