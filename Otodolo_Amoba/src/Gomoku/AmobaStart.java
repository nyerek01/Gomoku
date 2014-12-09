package Gomoku;


import Gomoku.AmobaGUI;


class AmobaStart {

    private static AmobaGUI amoba;
    private static XMLLogic xmllogic;
//    private static StepLogic steplogic;



    public static void AmobaStart () {
        setLogic (new XMLLogic ());
//        XMLLogic logic1 = new XMLLogic ();
//                setStepLogic( new StepLogic());
        setGui (new AmobaGUI ());
//        new AmobaGUI ();

    }

    protected static XMLLogic getLogic () {
        return xmllogic;
    }

    protected static AmobaGUI getAmoba () {
        return amoba;
    }

//        protected static StepLogic getStepLogic () {
//        return steplogic;
//    }
    
    private static void setLogic (XMLLogic aLogic) {
        xmllogic = aLogic;
    }

    private static void setGui (AmobaGUI aGui) {
        amoba = aGui;
    }
//        private static void setStepLogic (StepLogic sl) {
//        steplogic = sl;
//    }
}
