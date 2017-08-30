import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RBTreeTest {

    RBTree rbTree;

    @Before
    public void setUp() {
        rbTree = new RBTree();
    }

    @After
    public void tearDown() {
        rbTree = null;
    }

    //{Root}
    @Test
    public void getRootTest () {
        Assert.assertEquals("Check for infinity node", rbTree.getRoot().Parent.Key, Integer.MAX_VALUE);
    }

    //{empty, non-empty}
    @Test
    public void emptyTest() {
        //Check for empty tree
        Assert.assertEquals("Check for empty tree", rbTree.empty(), true);

        rbTree.insert(100, "One hundred");

        //Check for non-empty tree
        Assert.assertEquals("Check for non-empty tree", rbTree.empty(), false);
    }
    //{empty, i100i150}
    @Test
    public void searchTest() {
        //check empty tree
        Assert.assertEquals("Check for null return on empty tree", rbTree.search(100), null);

        //check for value not in tree
        rbTree.insert(100, "One hundred");
        Assert.assertEquals("Check for null when value is not in tree", rbTree.search(50), null);

        //check for value found to right
        rbTree.insert(150, "One hundred and fifty");
        Assert.assertEquals("Check for value of search return", rbTree.search(150), "One hundred and fifty");
    }

    //{i100i50i200i250i150i75i25i300i325}
    @Test
    public void insertTest() {
        //check initial insert
        rbTree.insert(100, "One hundred");
        Assert.assertEquals("Check for value for root node", rbTree.getRoot().Value, "One hundred");

        //check left insert
        rbTree.insert(50, "Fifty");
        Assert.assertEquals("Check for value of left node", rbTree.getRoot().Left.Value, "Fifty");
        Assert.assertEquals("Check value of left node parent", rbTree.getRoot().Left.Parent.Value, "One hundred");

        //check right insert
        rbTree.insert(200, "Two hundred");
        Assert.assertEquals("Check for value of right node", rbTree.getRoot().Right.Value, "Two hundred");
        Assert.assertEquals("Check value of right node parent", rbTree.getRoot().Right.Parent.Value, "One hundred");

        //check right right insert
        rbTree.insert(250, "Two hundred and fifty");
        Assert.assertEquals("Check for value of right right node", rbTree.getRoot().Right.Right.Value, "Two hundred and fifty");
        Assert.assertEquals("Check value of right right node parent", rbTree.getRoot().Right.Right.Parent, rbTree.getRoot().Right);

        //check right left insert
        rbTree.insert(150, "One hundred and fifty");
        Assert.assertEquals("Check for value of right left node", rbTree.getRoot().Right.Left.Value, "One hundred and fifty");
        Assert.assertEquals("Check value of right left node parent", rbTree.getRoot().Right.Left.Parent, rbTree.getRoot().Right);

        //check left right insert
        rbTree.insert(75, "Seventy-five");
        Assert.assertEquals("Check for value of left right node", rbTree.getRoot().Left.Right.Value, "Seventy-five");
        Assert.assertEquals("Check value of left right node parent", rbTree.getRoot().Left.Right.Parent, rbTree.getRoot().Left);

        //check left left insert
        rbTree.insert(25, "Twenty-five");
        Assert.assertEquals("Check for value of left left node", rbTree.getRoot().Left.Left.Value, "Twenty-five");
        Assert.assertEquals("Check value of left left node parent", rbTree.getRoot().Left.Left.Parent, rbTree.getRoot().Left);

        rbTree.insert(300, "Three hundred");
        rbTree.insert(325, "Three hundred and twenty-five");

        //check for duplicate value
        Assert.assertEquals("Check for return value of duplicate insert", rbTree.insert(25, "Twenty-five"), -1);
    }

    //{ null-100-150f150i250f250i350f350i225i325i230i300i400i450 },
    @Test
    public void fixUpTest(){

        RBTree.RBNode rootNode = rbTree.new RBNode("One hundred", 100, null, null, rbTree.getRoot().Parent);
        rbTree.getRoot().Parent.Left = rootNode;
        rootNode.Left = rbTree.createNullNode(rootNode);
        rootNode.Right = rbTree.createNullNode(rootNode);


        RBTree.RBNode rightNode = rbTree.new RBNode("One hundred and fifty", 150, null, null, rootNode);
        rootNode.Right = rightNode;
        rightNode.Left = rbTree.createNullNode(rightNode);
        rightNode.Right = rbTree.createNullNode(rightNode);

        rbTree.fixUpTree(rightNode);

        RBTree.RBNode rightRightNode = rbTree.new RBNode("Two hundred and fifty", 250, null, null, rightNode);
        rightNode.Right = rightRightNode;
        rightRightNode.Left = rbTree.createNullNode(rightRightNode);
        rightRightNode.Right = rbTree.createNullNode(rightRightNode);


        rbTree.fixUpTree(rightRightNode);

        RBTree.RBNode rightRightRightNode = rbTree.new RBNode("Three hundred and fifty", 350, null, null, rightRightNode);
        rightRightNode.Right = rightRightRightNode;
        rightRightRightNode.Left = rbTree.createNullNode(rightRightRightNode);
        rightRightRightNode.Right = rbTree.createNullNode(rightRightRightNode);

        rbTree.fixUpTree(rightRightRightNode);
        rbTree.insert(225, "Two hundred and twenty-five");
        rbTree.insert(325, "Three hundred and twenty-five");
        rbTree.insert(230, "Two hundred and thirty");
        rbTree.insert(300, "Three hundred");
        rbTree.insert(400, "Four hundred");
        rbTree.insert(450, "Four hundred and fifty");
        Assert.assertEquals("Check value of root", rbTree.getRoot().Value, "Two hundred and fifty");
        Assert.assertEquals("Check value of left node of root", rbTree.getRoot().Left.Value, "One hundred and fifty");
        Assert.assertEquals("Check value of right node of root", rbTree.getRoot().Right.Value, "Three hundred and twenty-five");
        Assert.assertEquals("Check value of right node of right node of root", rbTree.getRoot().Right.Right.Value, "Four hundred");
    }

    //{100-150-250}
    @Test
    public void leftRotateTest(){
        RBTree.RBNode rootNode = rbTree.new RBNode("One hundred", 100, null, null, rbTree.getRoot().Parent);
        rbTree.getRoot().Parent.Left = rootNode;
        rootNode.Left = rbTree.createNullNode(rootNode);
        rootNode.Right = rbTree.createNullNode(rootNode);


        RBTree.RBNode rightNode = rbTree.new RBNode("One hundred and fifty", 150, null, null, rootNode);
        rootNode.Right = rightNode;
        rightNode.Left = rbTree.createNullNode(rightNode);
        rightNode.Right = rbTree.createNullNode(rightNode);


        RBTree.RBNode rightRightNode = rbTree.new RBNode("Two hundred and fifty", 250, null, null, rightNode);
        rightNode.Right = rightRightNode;
        rightRightNode.Left = rbTree.createNullNode(rightRightNode);
        rightRightNode.Right = rbTree.createNullNode(rightRightNode);


        rbTree.leftRotate(rbTree.getRoot());

        Assert.assertEquals("Check value of root", rbTree.getRoot().Value, "One hundred and fifty");
        Assert.assertEquals("Check value of node right of root", rbTree.getRoot().Right.Value, "Two hundred and fifty");
        Assert.assertEquals("Check value of node left of root", rbTree.getRoot().Left.Value, "One hundred");

    }
    //{{25-50-null}-100-null}
    @Test
    public void rightRotateTest(){
        RBTree.RBNode rootNode = rbTree.new RBNode("One hundred", 100, null, null, rbTree.getRoot().Parent);
        rbTree.getRoot().Parent.Left = rootNode;
        rootNode.Left = rbTree.createNullNode(rootNode);
        rootNode.Right = rbTree.createNullNode(rootNode);


        RBTree.RBNode leftNode = rbTree.new RBNode("Fifty", 50, null, null, rootNode);
        rootNode.Left = leftNode;
        leftNode.Left = rbTree.createNullNode(leftNode);
        leftNode.Right = rbTree.createNullNode(leftNode);


        RBTree.RBNode leftLeftNode = rbTree.new RBNode("Twenty-five", 25, null, null, leftNode);
        leftNode.Left = leftLeftNode;
        leftLeftNode.Left = rbTree.createNullNode(leftLeftNode);
        leftLeftNode.Right = rbTree.createNullNode(leftLeftNode);


        rbTree.rightRotate(rbTree.getRoot());

        Assert.assertEquals("Check value of root", rbTree.getRoot().Value, "Fifty");
        Assert.assertEquals("Check value of node right of root", rbTree.getRoot().Right.Value, "One hundred");
        Assert.assertEquals("Check value of node left of root", rbTree.getRoot().Left.Value, "Twenty-five");

    }

    /*
    left child-parent-right child delete node: ##-##-##d##
    Test path:
    {empty, null-100-150d100, 50-150-nulld150, 25-50-100d50, {null-25-75}-100-{125-150-null}d100}
     */
    @Test
    public void deleteTest() {
        Assert.assertEquals("Check delete on empty tree", rbTree.delete(100), -1);

        rbTree.insert(100, "One hundred");
        rbTree.delete(100);
        Assert.assertEquals("Delete one node", rbTree.empty(), true);

        rbTree.insert(100, "One hundred");
        rbTree.insert(150, "One hundred and fifty");
        rbTree.delete(100);
        Assert.assertEquals("Check after left null right child delete", rbTree.getRoot().Value, "One hundred and fifty");

        rbTree.insert(50, "Fifty");
        rbTree.delete(150);
        Assert.assertEquals("Check after right null left child  delete", rbTree.getRoot().Value, "Fifty");


        rbTree.insert(25, "Twenty-five");
        rbTree.insert(100, "One hundred");
        rbTree.delete(50);
        Assert.assertEquals("Check after right and left child delete", rbTree.getRoot().Value, "One hundred");
        Assert.assertEquals("Check after right and left child delete", rbTree.getRoot().Left.Value, "Twenty-five");
        Assert.assertEquals("Check after right and left child delete", rbTree.getRoot().Right.Value, "N");
        Assert.assertEquals("Check after right and left child delete", rbTree.getRoot().Left.Parent.Value, "One hundred");
        Assert.assertEquals("Check after right and left child delete", rbTree.getRoot().Right.Parent.Value, "One hundred");


        rbTree.insert(150, "One hundred and fifty");
        rbTree.insert(75, "Seventy-five");
        rbTree.insert(125, "One hundred and twenty-five");
        rbTree.delete(100);
        Assert.assertEquals("Check after right and left child delete", rbTree.getRoot().Value, "One hundred and twenty-five");
        Assert.assertEquals("Check after right and left child delete", rbTree.getRoot().Left.Value, "Twenty-five");
        Assert.assertEquals("Check after right and left child delete", rbTree.getRoot().Right.Value, "One hundred and fifty");
        Assert.assertEquals("Check after right and left child delete", rbTree.getRoot().Left.Parent.Value, "One hundred and twenty-five");
        Assert.assertEquals("Check after right and left child delete", rbTree.getRoot().Right.Parent.Value, "One hundred and twenty-five");
    }

    public void createTree(){
        rbTree.insert(150, "One hundred and fifty");
        rbTree.insert(100, "One hundred");
        rbTree.insert(250, "Two hundred and fifty");
        rbTree.insert(50, "Fifty");
        rbTree.insert(200, "Two hundred");
        rbTree.insert(75, "Seventy-five");
        rbTree.insert(300, "Three hundred");
        rbTree.insert(25, "Twenty-five");
    }

    //{{{25-50-null}-100-75}-150-{200-250-300}d100d25i25i100i325d25}
    @Test
    public void deleteFixupTest(){
        //create initial tree
        createTree();

        //right child case 4
        rbTree.delete(100);
        Assert.assertEquals("Check deleted 100", null, rbTree.search(100));

        //left child case 2
        rbTree.delete(25);
        Assert.assertEquals("Check deleted 25", null, rbTree.search(25));


        rbTree.insert(25, "Twenty-five");
        rbTree.insert(100, "One hundred");
        rbTree.insert(325, "Three hundred and twenty-five");

        //left child case 4
        rbTree.delete(25);
        Assert.assertEquals("Check deleted 25", null, rbTree.search(25));
        Assert.assertEquals("Check root", "One hundred and fifty", rbTree.getRoot().Value);
    }

    //{20-30-{35-40-50}d20
    @Test
    public void deleteFixupTestLeftCaseFour(){
        rbTree.insert(30, "Thirty");
        rbTree.insert(20, "Twenty");
        rbTree.insert(40, "Forty");
        rbTree.insert(35, "Thirty-five");
        rbTree.insert(50, "Fifty");
        rbTree.delete(20);
        Assert.assertEquals("Check deleted 20", null, rbTree.search(20));
    }

    //{{{3-5-null}-10-20}-30-50}
    @Test
    public void deleteFixupTestRightCaseOneAndTwo(){
        rbTree.insert(30, "Thirty");
        rbTree.insert(20, "Twenty");
        rbTree.insert(50, "Fifty");
        rbTree.insert(10, "Ten");
        rbTree.insert(5, "Five");
        rbTree.insert(3, "Three");
        rbTree.delete(50);
        Assert.assertEquals("Check deleted 50", null, rbTree.search(50));
    }

    //{empty, i100i50i200i250i350i25}
    @Test
    public void minTest() {
        Assert.assertEquals("Check for empty tree", rbTree.min(), null);

        rbTree.insert(100, "One hundred");
        Assert.assertEquals("Check tree with one value", rbTree.min(), "One hundred");

        rbTree.insert(50, "Fifty");
        Assert.assertEquals("Check for one insert to left", rbTree.min(), "Fifty");

        rbTree.insert(200, "Two hundred");
        rbTree.insert(250, "Two hundred and fifty");
        rbTree.insert(350, "Three hundred and fifty");

        //Check insert after rotation
        rbTree.insert(25, "Twenty-five");
        Assert.assertEquals("Check for value of right node after rotation", rbTree.min(), "Twenty-five");
    }

    //{empty, i100i200i50i25i10i300}
    @Test
    public void maxTest() {
        Assert.assertEquals("Check for empty tree", rbTree.max(), null);

        rbTree.insert(100, "One hundred");
        Assert.assertEquals("Check tree with one value", rbTree.max(), "One hundred");

        rbTree.insert(200, "Two hundred");
        Assert.assertEquals("Check for one insert to right", rbTree.max(), "Two hundred");

        rbTree.insert(50, "Fifty");
        rbTree.insert(25, "Twenty-five");
        rbTree.insert(10, "Ten");

        //Check insert after rotation
        rbTree.insert(300, "Three hundred");
        Assert.assertEquals("Check for value of right node after rotation", rbTree.max(), "Three hundred");
    }


    @Test
    public void keysToArrayTest() {
        int[] array = rbTree.keysToArray();
        Assert.assertEquals("Check values on empty tree", array.length, 0);

        rbTree.insert(150, "One hundred and fifty");
        rbTree.insert(200, "Two hundred");
        array = rbTree.keysToArray();
        Assert.assertEquals("Check values of array", array[0], 150);
        Assert.assertEquals("Check values of array", array[1], 200);

        rbTree.insert(250, "Two hundred and fifty");
        rbTree.insert(50, "Fifty");

        array = rbTree.keysToArray();

        Assert.assertEquals("Check values of array", array[0], 50);
        Assert.assertEquals("Check values of array", array[1], 150);
        Assert.assertEquals("Check values of array", array[2], 200);
        Assert.assertEquals("Check values of array", array[3], 250);
    }

    @Test
    public void valuesToArrayTest() {
        String[] array = rbTree.valuesToArray();
        Assert.assertEquals("Check values on empty tree", array[0], "");

        rbTree.insert(150, "One hundred and fifty");
        rbTree.insert(200, "Two hundred");
        array = rbTree.valuesToArray();
        Assert.assertEquals("Check values of array", array[0], "One hundred and fifty");
        Assert.assertEquals("Check values of array", array[1], "Two hundred");

        rbTree.insert(250, "Two hundred and fifty");
        rbTree.insert(50, "Fifty");

        array = rbTree.valuesToArray();

        Assert.assertEquals("Check values of array", array[0], "Fifty");
        Assert.assertEquals("Check values of array", array[1], "One hundred and fifty");
        Assert.assertEquals("Check values of array", array[2], "Two hundred");
        Assert.assertEquals("Check values of array", array[3], "Two hundred and fifty");
    }


    //Untestable method - no return value
    @Test
    public void printTest() {
        rbTree.print();
    }

    //{empty, i100i50i150i200}
    @Test
    public void sizeTest() {
        Assert.assertEquals("Check for empty tree size", rbTree.size(), 0);

        rbTree.insert(100, "One hundred");
        Assert.assertEquals("Check one node", rbTree.size(), 1);

        rbTree.insert(50, "Fifty");
        rbTree.insert(150, "One hundred and fifty");
        rbTree.insert(200, "Two hundred");

        Assert.assertEquals("Check after inserts", rbTree.size(), 4);
    }

    //{i100i50}
    @Test
    public void sizeCalcTest() {
        rbTree.insert(100, "One hundred");
        Assert.assertEquals("Check root element", rbTree.sizeCalc(rbTree.getRoot()), 1);

        rbTree.insert(50, "Fifty");
        Assert.assertEquals("Check root element", rbTree.sizeCalc(rbTree.getRoot()), 2);
    }
}
