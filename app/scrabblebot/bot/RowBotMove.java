package scrabblebot.bot;

public class RowBotMove {

        int start;
        String word;

        RowBotMove(int start, String word) {
            this.start = start;
            this.word = word;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RowBotMove that = (RowBotMove) o;

            if (start != that.start) return false;
            if (!word.equals(that.word)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = start;
            result = 31 * result + word.hashCode();
            return result;
        }

}
