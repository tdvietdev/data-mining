/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.mining;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author tdvdev
 */
public class HomePanel extends javax.swing.JPanel {

    private int totalTransaction;
    private int optionInput;
    private String linkInput;
    private List<Set<String>> itemsetList;
    private APriori<String> serviceImpl;
    private int subset;
    private float confidence;
    private String dataInput;

    /**
     * Creates new form HomePanel
     */
    public HomePanel() {
        this.totalTransaction = 0;
        this.subset = 0;
        this.confidence = (float) 0.5;
        optionInput = 2;
        List<Set<String>> itemsetList = new ArrayList<>();
        serviceImpl = new APriori<>();
        initComponents();
        lbSubset.setText(String.valueOf(this.subset));
        lbConfidence.setText(String.valueOf(this.confidence));
        this.dataInput = "";

    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        radSet = new javax.swing.JRadioButton();
        radFile = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        btnDataInput = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        sldSubset = new javax.swing.JSlider();
        sldConfidence = new javax.swing.JSlider();
        lbSubset = new javax.swing.JLabel();
        lbConfidence = new javax.swing.JLabel();
        lbTransaction = new javax.swing.JLabel();
        javax.swing.JButton btnResult = new javax.swing.JButton();
        btnData = new javax.swing.JButton();

        setForeground(java.awt.Color.blue);
        setAutoscrolls(true);
        setFocusCycleRoot(true);

        jPanel1.setBackground(java.awt.Color.cyan);

        buttonGroup1.add(radSet);
        radSet.setText("Nhap du lieu bang tay");
        radSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radSetActionPerformed(evt);
            }
        });

        buttonGroup1.add(radFile);
        radFile.setSelected(true);
        radFile.setText("Nhap du lieu qua file");
        radFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radFileActionPerformed(evt);
            }
        });

        jLabel2.setText("Nhap du lieu");

        btnDataInput.setText("Mo giao dien nhap lieu");
        btnDataInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataInputActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(radSet)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(radFile)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(btnDataInput, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radFile)
                    .addComponent(radSet))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDataInput)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(java.awt.Color.green);

        jLabel1.setText("Cai dat");

        jLabel3.setText("Subset");

        jLabel4.setText("Confidence");

        sldSubset.setMajorTickSpacing(10);
        sldSubset.setMinorTickSpacing(5);
        sldSubset.setPaintLabels(true);
        sldSubset.setPaintTicks(true);
        sldSubset.setSnapToTicks(true);
        sldSubset.setToolTipText("");
        sldSubset.setFocusable(false);
        sldSubset.setOpaque(true);
        sldSubset.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldSubsetStateChanged(evt);
            }
        });

        sldConfidence.setMajorTickSpacing(10);
        sldConfidence.setMinorTickSpacing(5);
        sldConfidence.setPaintLabels(true);
        sldConfidence.setPaintTicks(true);
        sldConfidence.setSnapToTicks(true);
        sldConfidence.setInheritsPopupMenu(true);
        sldConfidence.setOpaque(true);
        sldConfidence.setValueIsAdjusting(true);
        sldConfidence.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldConfidenceStateChanged(evt);
            }
        });

        lbTransaction.setText("No transaction!");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbTransaction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbSubset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbConfidence, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sldSubset, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                            .addComponent(sldConfidence, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbSubset, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(sldSubset, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbConfidence, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(sldConfidence, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                .addContainerGap())
        );

        btnResult.setText("Hien thi ket qua");
        btnResult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResultActionPerformed(evt);
            }
        });

        btnData.setText("Xem du lieu");
        btnData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(88, Short.MAX_VALUE)
                .addComponent(btnData, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(btnResult)
                .addGap(106, 106, 106))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnResult, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnData))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnResultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResultActionPerformed
        // TODO add your handling code here:

        Map<Set<String>, Integer> frequentItemSets = serviceImpl.generateFrequentItemSets(this.itemsetList, this.subset);
//                                System.out.println(frequentItemSets);
        FrameResult frResult = new FrameResult(frequentItemSets);
        frResult.setVisible(true);
//        System.out.println(serviceImpl.printCandidatesResult(frequentItemSets));
        serviceImpl.ruleGeneration(frequentItemSets, this.confidence);

    }//GEN-LAST:event_btnResultActionPerformed

    private void btnDataInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataInputActionPerformed
        if (this.optionInput == 2) {
            int mChose = -1;
            String datasource = "";
            JFileChooser choseInput = new JFileChooser();
            choseInput.setDialogTitle("Open a file");
            FileFilter filter = new ExtensionFileFilter("txt,xls", new String[]{"TXT", "XLS"});
            choseInput.setFileFilter(filter);
            mChose = choseInput.showOpenDialog(null);
            if (mChose == JFileChooser.APPROVE_OPTION) {
                datasource = choseInput.getSelectedFile().toString();
                String basket = "";
                List<Set<String>> transactions = new ArrayList<Set<String>>();
                try {
                    FileReader fileReader = new FileReader(datasource);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    while ((basket = bufferedReader.readLine()) != null) {
                        String items[] = basket.split(" ");
                        transactions.add(new HashSet<String>(Arrays.asList(items)));
                    }
                    // Always close files.
                    bufferedReader.close();
                } catch (FileNotFoundException ex) {
                    System.out.println("Unable to open file '" + datasource + "'");
                } catch (IOException ex) {
                    System.out.println("Error reading file '" + datasource + "'");
                }
                this.itemsetList = transactions;
                this.totalTransaction = transactions.size();
                lbTransaction.setText("Co : " + this.totalTransaction + " giao dich");
                int mVa = sldSubset.getValue();
                this.subset = mVa * this.totalTransaction / 100;
                lbSubset.setText(String.valueOf(this.subset));
            }
        } else {

        }
    }//GEN-LAST:event_btnDataInputActionPerformed

    private void sldSubsetStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldSubsetStateChanged
        // TODO add your handling code here:
        int mVa = sldSubset.getValue();
        this.subset = mVa * this.totalTransaction / 100;
        lbSubset.setText(String.valueOf(this.subset));
    }//GEN-LAST:event_sldSubsetStateChanged

    private void radFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radFileActionPerformed
        // TODO add your handling code here:
        this.optionInput = 2;
    }//GEN-LAST:event_radFileActionPerformed

    private void radSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radSetActionPerformed
        // TODO add your handling code here:
        this.optionInput = 1;
    }//GEN-LAST:event_radSetActionPerformed

    private void btnDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataActionPerformed
        // TODO add your handling code here:
        if (itemsetList != null) {
            String mTr = "";
            mTr += "<html>";
            for (Set<String> set : itemsetList) {
                mTr += set;
                mTr += "<br>";
            }

            mTr += "</html>";
            DataInput dataInput = new DataInput(this.subset, this.confidence, mTr);
            dataInput.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(btnDataInput, "Ban chua nhap du lieu");
        }

    }//GEN-LAST:event_btnDataActionPerformed

    private void sldConfidenceStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldConfidenceStateChanged
        // TODO add your handling code here:
        this.confidence = sldConfidence.getValue() / (float) 100;
        lbConfidence.setText(String.valueOf(this.confidence));
    }//GEN-LAST:event_sldConfidenceStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnData;
    private javax.swing.JButton btnDataInput;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbConfidence;
    private javax.swing.JLabel lbSubset;
    private javax.swing.JLabel lbTransaction;
    private javax.swing.JRadioButton radFile;
    private javax.swing.JRadioButton radSet;
    private javax.swing.JSlider sldConfidence;
    private javax.swing.JSlider sldSubset;
    // End of variables declaration//GEN-END:variables
}
