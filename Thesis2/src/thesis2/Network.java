package thesis2;

abstract class Network
{
    protected double[] input, output;
    protected LayerList layerList;

    public Network()
    {
        layerList = new LayerList();
    }

    /**
     * This method is used to add a final layer to a network
     * @param layerType
     * @param layerSize
     * @param activation 
     */
    public void addLayer(String layerType, int layerSize, String activation)
    {
        layerList.add(new Layer(layerType, layerSize, activation));
    }
    
    /**
     * This method is used to add an input or middle layer
     * @param layerType
     * @param layerSize
     * @param nextLayerSize
     * @param activation 
     */
    public void addLayer(
            String layerType, int layerSize, 
            int nextLayerSize, String activation)
    {
        layerList.add(
                new Layer(layerType, layerSize, nextLayerSize, activation));
    }
    
    public Layer layerFromList(int index)
    {
        return layerList.get(index);
    }
}
