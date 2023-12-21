import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

public class GUI extends JFrame {
    // Constants
    private static final String HOME_CARD = "home";
    private static final String ADD_OR_GET_CARD = "addorget";
    private static final String ADD_FOOD_CARD = "addfood";
    private static final String SHOW_ALL_FOODS_CARD = "showallfoods";
    private static final Font TEXT_FONT = new Font("Verdana", Font.BOLD, 16);
    private static final Font SMALLER_FONT = new Font("Verdana", Font.BOLD, 12);
    private static final Color BACKGROUND_COLOR = new Color(255, 51, 51);
    private static final Color SECONDARY_COLOR = new Color(255, 255, 153);

    // UI Components
    private JPanel mainPanel;
    JComboBox<Food.TypeOfFood> addTypeOfFoodDropBox = new JComboBox<>();
    JComboBox<Food.TypeOfFood> showTypeOfFoodBox = new JComboBox<>();
    JLabel showDishLabel = new JLabel("");
    JLabel showTimeLabel = new JLabel("");
    JLabel showTypeLabel = new JLabel("Typ: ");
    private JTextArea showAllFoodsText = new JTextArea();
    private JTextArea addNameTextArea = new JTextArea(2,10);
    private JTextArea addTimeTextArea = new JTextArea(2,10);
    JScrollPane showAllFoodsSP = new JScrollPane(showAllFoodsText);
    private CardLayout cards;
    private Desktop desktop;
    private ArrayList<Food> consumableArrayList;
    ArrayList<Food> sortedFoodList;
    GridBagConstraints gbc = new GridBagConstraints();
    FoodFactory factory = new SimpleFoodFactory();


    public GUI() {
        FileHandler.getInstance();
        initializeFrame();
        initializeComponents();

    }

    private void initializeFrame() {
        cards = new CardLayout();
        mainPanel = new JPanel(cards);
        consumableArrayList = FileHandler.readListFromFile();
        desktop = Desktop.getDesktop();
        this.add(mainPanel);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 400);
    }

    private void initializeComponents() {
        createHomePanel();
        createAddOrGetPanel();
        createAddFoodPanel();
        createShowRecipePanel();
        createShowAllFoodsPanel();
    }
    public JLabel createJlabel(String labelText, Font font){
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        return label;
    }
    private void getFood1() {
        Collections.shuffle(consumableArrayList);
        showDishLabel.setText("Maträtt: "
                + consumableArrayList.get(0).name);
        showTimeLabel.setText("Tillagningstid: "
                + consumableArrayList.get(0).timeToPrepare + " minuter");
        cards.show(mainPanel,"showrecipe");
    }

    private void getFood() {
        Collections.shuffle(consumableArrayList);
        showDishLabel.setText("Maträtt: "
                + consumableArrayList.get(0).name);
        showTimeLabel.setText("Tillagningstid: "
                + consumableArrayList.get(0).timeToPrepare + " minuter");
        cards.show(mainPanel,"showrecipe");
    }

    private void addFood() {
        Food newFood = factory.createFood(addNameTextArea.getText(),
                Integer.parseInt(addTimeTextArea.getText()),
                addTypeOfFoodDropBox.getSelectedItem());
        consumableArrayList.add(newFood);
        FileHandler.writeListToFile(consumableArrayList);
        cards.show(mainPanel,"home");
        addNameTextArea.setText("");
        addTimeTextArea.setText("");
    }

    private void searchRecipe() {
        String encodedQuery = URLEncoder.encode(consumableArrayList.get(0).name + " recept", StandardCharsets.UTF_8);
        String searchURL = "https://www.google.com/search?q=" + encodedQuery;
        try {
            desktop.browse(new URI(searchURL));
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void backToStart() {
        cards.show(mainPanel,HOME_CARD);
    }

    private void showAllFoods() {
        StringBuilder sb = new StringBuilder();
        for (Consumable consumable: consumableArrayList) {
            showAllFoodsText.setText(String.valueOf(sb.append(consumable.name
                    + "\n" + consumable.timeToPrepare + " minuter" + "\n"+"\n")));
        }
        cards.show(mainPanel,SHOW_ALL_FOODS_CARD);
    }

    private void removeFood() {
        System.out.println("Du tog bort: " + consumableArrayList.get(0).name);
        consumableArrayList.remove(0);
        FileHandler.writeListToFile(consumableArrayList);
    }

    private void refreshFood1() {
        if (showTypeOfFoodBox.getSelectedIndex() == 0) {
            consumableArrayList = FileHandler.readListFromFile();
            Food removedFood = consumableArrayList.remove(0);
            consumableArrayList.add(removedFood);
            showDishLabel.setText(consumableArrayList.get(0).name);
            showTimeLabel.setText("Tillagningstid: " + consumableArrayList.get(0).timeToPrepare + " minuter");
            showTypeLabel.setText("Typ: " + consumableArrayList.get(0).dietType);
            cards.show(mainPanel,"showrecipe");
        } else if (showTypeOfFoodBox.getSelectedIndex() == 1) {
            consumableArrayList = FileHandler.readListFromFile();
            Collections.shuffle(consumableArrayList);
            SortingSpace(showTypeOfFoodBox.getSelectedItem());
        } else if (showTypeOfFoodBox.getSelectedIndex() == 2) {
            consumableArrayList = FileHandler.readListFromFile();
            Collections.shuffle(consumableArrayList);
            SortingSpace(showTypeOfFoodBox.getSelectedItem());
        } else if (showTypeOfFoodBox.getSelectedIndex() == 3) {
            consumableArrayList = FileHandler.readListFromFile();
            Collections.shuffle(consumableArrayList);
            SortingSpace(showTypeOfFoodBox.getSelectedItem());
        }

    }
    private void refreshFood() {
        if (showTypeOfFoodBox.getSelectedIndex() == 0) {
            consumableArrayList = FileHandler.readListFromFile();
            Food removedFood = consumableArrayList.remove(0);
            consumableArrayList.add(removedFood);
            FileHandler.writeListToFile(consumableArrayList);
            showDishLabel.setText(consumableArrayList.get(0).name);
            showTimeLabel.setText("Tillagningstid: " + consumableArrayList.get(0).timeToPrepare + " minuter");
            showTypeLabel.setText("Typ: " + consumableArrayList.get(0).dietType);
            cards.show(mainPanel,"showrecipe");
        } else if (showTypeOfFoodBox.getSelectedIndex() == 1) {
            consumableArrayList = FileHandler.readListFromFile();
            Collections.shuffle(consumableArrayList);
            SortingSpace(showTypeOfFoodBox.getSelectedItem());
        } else if (showTypeOfFoodBox.getSelectedIndex() == 2) {
            consumableArrayList = FileHandler.readListFromFile();
            Collections.shuffle(consumableArrayList);
            SortingSpace(showTypeOfFoodBox.getSelectedItem());
        } else if (showTypeOfFoodBox.getSelectedIndex() == 3) {
            consumableArrayList = FileHandler.readListFromFile();
            Collections.shuffle(consumableArrayList);
            SortingSpace(showTypeOfFoodBox.getSelectedItem());
        }

    }

    private void createShowRecipePanel() {
        JPanel showFoodPanel = new JPanel(new GridBagLayout());
        showFoodPanel.setBackground(BACKGROUND_COLOR);
        gbc.gridy = 0;
        addDropBox(showFoodPanel, showTypeOfFoodBox);
        gbc.gridy = 1;
        showFoodPanel.add(showDishLabel,gbc);
        showDishLabel.setFont(SMALLER_FONT);
        gbc.gridy = 2;
        showFoodPanel.add(showTimeLabel,gbc);
        showTimeLabel.setFont(SMALLER_FONT);
        gbc.gridy = 3;
        showFoodPanel.add(createButton("Sök igen",TEXT_FONT,e->refreshFood()),gbc);
        gbc.gridy = 4;
        showFoodPanel.add(createButton("Sök recept",TEXT_FONT,e->searchRecipe()),gbc);
        gbc.gridy = 5;
        showFoodPanel.add(createButton("Ta bort maträtt",TEXT_FONT,e->removeFood()),gbc);
        gbc.gridy = 6;
        showFoodPanel.add(createButton("Visa alla maträtter",TEXT_FONT,e->showAllFoods()),gbc);
        gbc.gridy = 7;
        showFoodPanel.add(createButton("Tillbaka",TEXT_FONT,e->backToStart()),gbc);
        mainPanel.add(showFoodPanel,"showrecipe");
    }
    private void addDropBox(JPanel panel,JComboBox<Food.TypeOfFood> groupboxes) {
        panel.add(groupboxes);
        groupboxes.addItem(Food.TypeOfFood.ALLA);
        groupboxes.addItem(Food.TypeOfFood.MEAT);
        groupboxes.addItem(Food.TypeOfFood.VEGETARIAN);
        groupboxes.addItem(Food.TypeOfFood.VEGAN);
        groupboxes.setSelectedIndex(0);
    }
    public void SortingSpace(Object selectedType) {
        Filter filter = new Filter();
        sortedFoodList = filter.TypeOfFoodFilter(consumableArrayList,
                selectedType);
        consumableArrayList = sortedFoodList;
        showDishLabel.setText(consumableArrayList.get(0).name);
        showTimeLabel.setText("Tillagningstid: " + consumableArrayList.get(0).timeToPrepare + " minuter");
        showTypeLabel.setText("Typ: "+ consumableArrayList.get(0).dietType);
        cards.show(mainPanel,"showrecipe");
    }
    private void createAddFoodPanel() {
        JPanel addFoodPanel = new JPanel(new GridBagLayout());
        addFoodPanel.setBackground(BACKGROUND_COLOR);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);
        addDropBox(addFoodPanel,addTypeOfFoodDropBox);
        gbc.gridx = 0; gbc.gridy = 1;
        addFoodPanel.add(createJlabel("Maträtt:",SMALLER_FONT),gbc);
        gbc.gridx = 1;
        addFoodPanel.add(addNameTextArea,gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        addFoodPanel.add(createJlabel("Tillagningstid:",SMALLER_FONT),gbc);
        gbc.gridx = 1;
        addFoodPanel.add(addTimeTextArea,gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        addFoodPanel.add(createJlabel("Typ:",SMALLER_FONT), gbc);
        gbc.gridx = 1;
        addTypeOfFoodDropBox.removeItemAt(0);
        addFoodPanel.add(createButton("Lägg till maträtt",TEXT_FONT,e->addFood()),gbc);
        gbc.gridx = 0; gbc.gridy = 5;
        addFoodPanel.add(createButton("Tillbaka",TEXT_FONT,e->backToStart()),gbc);
        gbc.gridx = 0; gbc.gridy = 6;
        mainPanel.add(addFoodPanel,ADD_FOOD_CARD);
    }
    private void createShowAllFoodsPanel() {
        JPanel showAllFoodsPanel = new JPanel(new BorderLayout());
        showAllFoodsPanel.setBackground(BACKGROUND_COLOR);
        showAllFoodsPanel.add(createButton("Tillbaka",TEXT_FONT,e->backToStart()),BorderLayout.NORTH);
        showAllFoodsText.setFont(SMALLER_FONT);
        showAllFoodsText.setLineWrap(true);
        showAllFoodsText.setWrapStyleWord(true);
        showAllFoodsText.setBackground(SECONDARY_COLOR);
        showAllFoodsSP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        showAllFoodsPanel.add(showAllFoodsSP,BorderLayout.CENTER);
        mainPanel.add(showAllFoodsPanel,SHOW_ALL_FOODS_CARD);
    }

    private void showRandomFood() {
        Collections.shuffle(consumableArrayList);
        showDishLabel.setText("Maträtt: " +
                consumableArrayList.get(0).name);
        showTimeLabel.setText("Tillagningstid: " +
                consumableArrayList.get(0).timeToPrepare + " minuter");
        cards.show(mainPanel,ADD_OR_GET_CARD);
    }

    private JButton createButton(String text, Font font, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(SECONDARY_COLOR);
        button.addActionListener(actionListener);
        return button;
    }
    private void createHomePanel() {
        JPanel startPanel = new JPanel(new FlowLayout());
        startPanel.setBackground(BACKGROUND_COLOR);
        startPanel.add(createButton("Mat", TEXT_FONT, e -> showRandomFood()));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.add(startPanel);
        mainPanel.add(centerPanel, HOME_CARD);
    }
    private void createAddOrGetPanel() {
        JPanel secondPanel = new JPanel(new FlowLayout());
        JPanel sndCenterPanel = new JPanel(new GridBagLayout());
        sndCenterPanel.add(secondPanel);
        sndCenterPanel.setBackground(BACKGROUND_COLOR);
        secondPanel.setBackground(BACKGROUND_COLOR);
        secondPanel.add(createButton("Lägg till",TEXT_FONT,e -> cards.show(mainPanel,"addfood")));
        secondPanel.add(createButton("Hitta maträtt",TEXT_FONT,e-> getFood()));
        mainPanel.add(sndCenterPanel,"addorget");
    }

    // Main method
    public static void main(String[] args) {
        new GUI();
    }
}

