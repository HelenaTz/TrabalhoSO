package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class Aplicacao {

    private JFrame frmGerenciadorDeTarefas;
    private JTable tabela;
    private JTextField textField;    
    public DefaultTableModel modeloTabela = new DefaultTableModel(
    		new Object[][] {},
            new String[] { "PID", "Nome da Tarefa", "Usuário", "Caminho do programa", "Memória (%)", "CPU (%)" }) {
        private static final long serialVersionUID = 1L;

        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    Aplicacao window = new Aplicacao();
                    window.frmGerenciadorDeTarefas.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Aplicacao() {
        initialize();
    }

    private void initialize() {
        frmGerenciadorDeTarefas = new JFrame();
        frmGerenciadorDeTarefas.setTitle("Gerenciador de Tarefas - Luiza e Helena");
        frmGerenciadorDeTarefas.setBounds(100, 100, 968, 604);
        frmGerenciadorDeTarefas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmGerenciadorDeTarefas.getContentPane().setLayout(null);

        
        JPanel panelProcessos = new JPanel();
        panelProcessos.setBounds(10, 89, 932, 417);
        frmGerenciadorDeTarefas.getContentPane().add(panelProcessos);
        panelProcessos.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 932, 417);
        panelProcessos.add(scrollPane);

        tabela = new JTable();
        
        tabela.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tabela.setRowHeight(20);
        tabela.setGridColor(Color.lightGray);
        tabela.setModel(new DefaultTableModel(new Object[][] {},
                new String[] { "PID", "Nome da Tarefa", "Usuário", "Caminho do programa", "Memória (%)", "CPU (%)" }) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(400);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(100);
        scrollPane.setViewportView(tabela);

        modeloTabela = (DefaultTableModel) tabela.getModel();
        getProcesses(modeloTabela);
        
        JButton btnFinalizarTarefa = new JButton("Finalizar Tarefa");
        btnFinalizarTarefa.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnFinalizarTarefa.setBounds(727, 529, 203, 30);
        frmGerenciadorDeTarefas.getContentPane().add(btnFinalizarTarefa);


        JButton btnPanelProcessos = new JButton("Atualizar");
        btnPanelProcessos.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnPanelProcessos.setBounds(597, 529, 120, 30);
        frmGerenciadorDeTarefas.getContentPane().add(btnPanelProcessos);

        JComboBox<String> comboBoxFields = new JComboBox<String>();
        comboBoxFields.setModel(new DefaultComboBoxModel<String>(
                new String[] { "PID", "Nome da Tarefa", "Usuário", "Caminho do programa" }));
        comboBoxFields.setFont(new Font("Tahoma", Font.PLAIN, 12));
        comboBoxFields.setBounds(10, 43, 160, 22);
        frmGerenciadorDeTarefas.getContentPane().add(comboBoxFields);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
        textField.setBounds(180, 44, 224, 20);
        frmGerenciadorDeTarefas.getContentPane().add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel = new JLabel("Filtrar processo");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel.setBounds(10, 19, 120, 14);
        frmGerenciadorDeTarefas.getContentPane().add(lblNewLabel);
        
        JButton btnExecutarNovaTarefa = new JButton("Executar Nova Tarefa");
        btnExecutarNovaTarefa.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
        	        Robot robozin = new Robot();
        	        robozin.keyPress(KeyEvent.VK_WINDOWS);
        	        robozin.keyPress(KeyEvent.VK_R);
        	        robozin.keyRelease(KeyEvent.VK_R);
        	        robozin.keyRelease(KeyEvent.VK_WINDOWS);
        	    } catch (Exception e1) {
        	        e1.printStackTrace();
        	    }
        	}
        });
        btnExecutarNovaTarefa.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnExecutarNovaTarefa.setBounds(727, 49, 215, 30);
        frmGerenciadorDeTarefas.getContentPane().add(btnExecutarNovaTarefa);
                
        btnFinalizarTarefa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tabela.getSelectedRow();
                if (linhaSelecionada >= 0) {
                    int confirmaMens = JOptionPane.showConfirmDialog(frmGerenciadorDeTarefas,
                            "Tem certeza de que deseja finalizar a tarefa selecionada?",
                            "Confirmação",
                            JOptionPane.YES_NO_OPTION);
                    if (confirmaMens == JOptionPane.YES_OPTION) {
                        long pid = (long) tabela.getValueAt(linhaSelecionada, 0);
                        ProcessHandle.allProcesses().forEach(result -> {
                            if (result.pid() == pid) {
                                result.destroy();
                            }
                        });
                        modeloTabela.removeRow(linhaSelecionada);
                    }
                }
            }
        });
        
        tabela.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) { 
                    int linha = tabela.rowAtPoint(e.getPoint());
                    if (linha >= 0 && linha < tabela.getRowCount()) {
                        tabela.setRowSelectionInterval(linha, linha);
                        mostraFinalizarTarefa(e.getX(), e.getY()); 
                    }
                }
            }
            
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
                    int linhaSelecionada = tabela.getSelectedRow();
                    if (linhaSelecionada >= 0) {
                        long pid = (long) tabela.getValueAt(linhaSelecionada, 0);
                        mostraInformacoes(e.getX(), e.getY(), pid);
                    }
                }
            }
        });



        btnPanelProcessos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelProcessos.setVisible(true);
                textField.setText(null);
                atualizarTarefas(); 
                tabela.setModel(modeloTabela);
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String campoSelecionado = comboBoxFields.getSelectedItem().toString().trim();
                String arg = textField.getText().toLowerCase();

                if (!arg.isEmpty()) {
                    DefaultTableModel modeloTabela = new DefaultTableModel(new Object[][] {},
                            new String[] { "PID", "Nome da Tarefa", "Usuário", "Caminho do programa", "Memória (%)", "CPU (%)" }) {
                    	private static final long serialVersionUID = 1L;

                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };

                    for (int linha = 0; linha < modeloTabela.getRowCount(); linha++) {
                        boolean match = false;
                        switch (campoSelecionado) {
                            case "PID":
                                long pid = (long) modeloTabela.getValueAt(linha, 0);
                                if (isLong(arg) && pid == Long.parseLong(arg)) {
                                    match = true;
                                }
                                break;
                            case "Nome da Tarefa":
                                String nomeTarefa = modeloTabela.getValueAt(linha, 1).toString().toLowerCase();
                                if (nomeTarefa.contains(arg)) {
                                    match = true;
                                }
                                break;
                            case "Usuário":
                            	
                                String user = modeloTabela.getValueAt(linha, 2).toString().toLowerCase();
                                if (user.contains(arg)) {
                                    match = true;
                                }
                                break;
                            case "Caminho do programa":
                                String caminhoPrograma = modeloTabela.getValueAt(linha, 3).toString().toLowerCase();
                                if (caminhoPrograma.contains(arg)) {
                                    match = true;
                                }
                                break;
                        }
                        if (match) {
                            modeloTabela.addRow(modeloTabela.getDataVector().elementAt(linha));
                        }
                    }
                    tabela.setModel(modeloTabela);
                    btnFinalizarTarefa.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            int linhaSelecionada = tabela.getSelectedRow();
                            if (linhaSelecionada >= 0) {
                                int mensagemConfirma = JOptionPane.showConfirmDialog(frmGerenciadorDeTarefas,
                                        "Tem certeza de que deseja finalizar a tarefa selecionada?",
                                        "Confirmação",
                                        JOptionPane.YES_NO_OPTION);
                                if (mensagemConfirma == JOptionPane.YES_OPTION) {
                                    long pid = (long) tabela.getValueAt(linhaSelecionada, 0);
                                    ProcessHandle.allProcesses().forEach(result -> {
                                        if (result.pid() == pid) {
                                            result.destroy();
                                        }
                                    });
                                    modeloTabela.removeRow(linhaSelecionada);
                                }
                            }
                        }
                    });
                    
                } else {
                    tabela.setModel(modeloTabela);
                }
            }
        });
    }

    private static boolean isLong(String str) {
        return str.matches("\\d+");
    }
    
    public void getProcesses(DefaultTableModel modeloTabela) {
        modeloTabela.setRowCount(0);
        ProcessHandle.allProcesses().filter(proc -> proc.info().user().isPresent()).map(proc -> {
            long pid = proc.pid();
            String nomeTarefa = getNomeDaTarefa(pid);
            String user = proc.info().user().get();
            String caminhoPrograma = proc.info().command().orElse("");
            String memory = getMemoria(pid);
            String cpu = getCPU(pid);
            return new Object[] { pid, nomeTarefa, user, caminhoPrograma, memory, cpu };
        }).forEach(row -> {
            if (row != null && row.length == modeloTabela.getColumnCount()) {
                modeloTabela.addRow(row);
            }
        });
    }


    private static String getNomeDaTarefa(long pid) {
        String nomeTarefa = "";
        try {
            String jvmName = ManagementFactory.getRuntimeMXBean().getName();
            if (jvmName.contains("@")) {
                jvmName = jvmName.split("@")[0];
            }
            if (jvmName.equals(Long.toString(pid))) {
                nomeTarefa = "Java Application";
            } else {
                ProcessBuilder contruiTarefa = new ProcessBuilder("cmd.exe", "/c",
                        "tasklist /FI \"PID eq " + pid + "\" /FO CSV /NH");
                contruiTarefa.redirectErrorStream(true);
                Process processo = contruiTarefa.start();
                BufferedReader leitor = new BufferedReader(new InputStreamReader(processo.getInputStream()));
                String linha;
                while ((linha = leitor.readLine()) != null) {
                    String[] fields = linha.split(",");
                    if (fields.length >= 2) {
                        nomeTarefa = fields[0].replaceAll("\"", "").trim();
                    }
                }
                leitor.close();
                processo.destroy();
            }
        } catch (Exception e) {
            nomeTarefa = "Unknown";
        }
        return nomeTarefa;
    }

    private static String getMemoria(long pid) {
        String comando = "tasklist /FI \"PID eq " + pid + "\" /FO CSV /NH";
        String memoria = "";

        try {
            Process processo = Runtime.getRuntime().exec(comando);
            BufferedReader leitor = new BufferedReader(new InputStreamReader(processo.getInputStream()));
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] campos = linha.split(",");
                if (campos.length >= 5) {
                    memoria = campos[4].replaceAll("\"", "").trim();
                }
            }
            leitor.close();
            processo.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return memoria;
    }

    private static String getCPU(long pid) {
        String comando = "wmic path Win32_PerfFormattedData_PerfProc_Process where IDProcess=" + pid + " get PercentProcessorTime";
        String cpu = "";

        try {
            Process processo = Runtime.getRuntime().exec(comando);
            BufferedReader leitor = new BufferedReader(new InputStreamReader(processo.getInputStream()));
            String linha;
            while ((linha = leitor.readLine()) != null) {
                linha = linha.trim();
                if (!linha.isEmpty() && !linha.startsWith("PercentProcessorTime")) {
                    cpu = linha;
                    break;
                }
            }
            leitor.close();
            processo.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cpu + "%";
    }
    
    private void mostraInformacoes(int x, int y, long pid) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem menuItem = new JMenuItem("Informações sobre a Tarefa");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                StringBuilder mensagem = new StringBuilder();
                mensagem.append("Informações detalhadas do processo (PID: ").append(pid).append("):\n\n");
                
                mensagem.append("Threads:\n");
                ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
                java.lang.management.ThreadInfo[] threadInfos = threadBean.getThreadInfo(threadBean.getAllThreadIds(), Integer.MAX_VALUE);
                for (java.lang.management.ThreadInfo threadInfo : threadInfos) {
                    mensagem.append("- ID: ").append(threadInfo.getThreadId()).append(", Nome: ").append(threadInfo.getThreadName()).append("\n");
                }
                mensagem.append("\n");
                
                mensagem.append("Informação de rede:\n");
                try {
                    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                    while (interfaces.hasMoreElements()) {
                        NetworkInterface rede = interfaces.nextElement();
                        mensagem.append("- Interface: ").append(rede.getName()).append("\n");
                        mensagem.append("  Endereços IP:\n");
                        Enumeration<java.net.InetAddress> enderecos = rede.getInetAddresses();
                        while (enderecos.hasMoreElements()) {
                            java.net.InetAddress pegaEndereco = enderecos.nextElement();
                            mensagem.append("  - ").append(pegaEndereco.getHostAddress()).append("\n");
                        }
                        mensagem.append("\n");
                    }
                } catch (SocketException e1) {
                    e1.printStackTrace();
                }
                
                
                JTextArea textArea = new JTextArea(mensagem.toString());
                textArea.setColumns(40);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(600, 400));

                JOptionPane.showMessageDialog(null, scrollPane, "Informações detalhadas do processo", JOptionPane.PLAIN_MESSAGE);
            }
        });
        

        popupMenu.add(menuItem);

        popupMenu.show(tabela, x, y);
    }

    
    private void mostraFinalizarTarefa(int x, int y) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem menuItem = new JMenuItem("Finalizar Tarefa");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tabela.getSelectedRow();
                if (linhaSelecionada >= 0) {
                    int menesagemConfirmar = JOptionPane.showConfirmDialog(frmGerenciadorDeTarefas,
                            "Tem certeza de que deseja finalizar a tarefa selecionada?",
                            "Confirmação",
                            JOptionPane.YES_NO_OPTION);
                    if (menesagemConfirmar == JOptionPane.YES_OPTION) {
                        long pid = (long) tabela.getValueAt(linhaSelecionada, 0);
                        ProcessHandle.allProcesses().forEach(result -> {
                            if (result.pid() == pid) {
                                result.destroy();
                            }
                        });
                        modeloTabela.removeRow(linhaSelecionada);
                    }
                }
            }
        });
        

        popupMenu.add(menuItem);

        popupMenu.show(tabela, x, y);
    }

    private void atualizarTarefas() {
        SwingWorker<Void, Void> s = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                getProcesses(modeloTabela);
                return null;
            }
        };

        s.execute();
    }
}