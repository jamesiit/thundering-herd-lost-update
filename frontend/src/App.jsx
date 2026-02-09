import Button from "./components/Button.jsx";
import Welcome from "./components/Welcome.jsx";
import {Toaster} from "sonner";
import InventoryCounter from "./components/InventoryCounter.jsx";

function App() {

  return (
      <>
          <Toaster richColors={true}/>
      <Welcome />
      <Button />
          <InventoryCounter />
      </>
  )

}

export default App
