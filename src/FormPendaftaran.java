import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FormPendaftaran extends JFrame {

    private final JTextField txtNama = new JTextField(20);
    private final JTextField txtEmail = new JTextField(20);
    private final JTextField txtTelp = new JTextField(20);
    private final JComboBox<String> cmbKegiatan = new JComboBox<>(new String[]{
            "Seminar", "Workshop", "Lomba", "Pelatihan"});
    private final JRadioButton rbPagi = new JRadioButton("Pagi", true);
    private final JRadioButton rbSiang = new JRadioButton("Siang");
    private final JRadioButton rbSore = new JRadioButton("Sore");
    private final JCheckBox cbKonsumsi = new JCheckBox("Konsumsi");
    private final JCheckBox cbSertifikat = new JCheckBox("Sertifikat");
    private final JCheckBox cbMateri = new JCheckBox("Materi cetak");
    private final JTextArea txtCatatan = new JTextArea(5, 20);
    private final JButton btnKirim = new JButton("Kirim");

    public FormPendaftaran() {
        super("Formulir Pendaftaran Kegiatan");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ButtonGroup grupSesi = new ButtonGroup();
        grupSesi.add(rbPagi);
        grupSesi.add(rbSiang);
        grupSesi.add(rbSore);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel judul = new JLabel("Pendaftaran Kegiatan", SwingConstants.CENTER);
        judul.setFont(judul.getFont().deriveFont(Font.BOLD, 18f));
        root.add(judul, BorderLayout.NORTH);

        root.add(buatPanelForm(), BorderLayout.CENTER);
        root.add(buatPanelTombol(), BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setMinimumSize(new Dimension(420, getHeight()));
        setLocationRelativeTo(null);

        setFocusTraversalPolicy(new UrutanFokus(List.of(
                txtNama, txtEmail, txtTelp, cmbKegiatan,
                rbPagi, rbSiang, rbSore,
                cbKonsumsi, cbSertifikat, cbMateri,
                txtCatatan, btnKirim)));
        setFocusTraversalPolicyProvider(true);
        getRootPane().setDefaultButton(btnKirim);
    }

    private JPanel buatPanelForm() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.anchor = GridBagConstraints.WEST;

        JPanel identitas = new JPanel(new GridBagLayout());
        identitas.setBorder(BorderFactory.createTitledBorder("Identitas"));
        tambahBaris(identitas, 0, "Nama:", txtNama, 'N');
        tambahBaris(identitas, 1, "Email:", txtEmail, 'E');
        tambahBaris(identitas, 2, "No. HP:", txtTelp, 'H');

        JPanel pilihan = new JPanel(new GridBagLayout());
        pilihan.setBorder(BorderFactory.createTitledBorder("Pilihan Kegiatan"));
        tambahBaris(pilihan, 0, "Jenis kegiatan:", cmbKegiatan, 'J');

        JPanel panelSesi = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panelSesi.add(rbPagi);
        panelSesi.add(rbSiang);
        panelSesi.add(rbSore);
        tambahBaris(pilihan, 1, "Sesi:", panelSesi, 0);

        JPanel panelFasilitas = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panelFasilitas.add(cbKonsumsi);
        panelFasilitas.add(cbSertifikat);
        panelFasilitas.add(cbMateri);
        tambahBaris(pilihan, 2, "Fasilitas:", panelFasilitas, 0);

        JPanel catatan = new JPanel(new BorderLayout());
        catatan.setBorder(BorderFactory.createTitledBorder("Catatan"));
        txtCatatan.setLineWrap(true);
        txtCatatan.setWrapStyleWord(true);
        txtCatatan.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        txtCatatan.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        catatan.add(new JScrollPane(txtCatatan), BorderLayout.CENTER);

        g.gridx = 0; g.fill = GridBagConstraints.HORIZONTAL; g.weightx = 1;
        g.gridy = 0; g.weighty = 0; p.add(identitas, g);
        g.gridy = 1; g.weighty = 0; p.add(pilihan, g);
        g.gridy = 2; g.weighty = 1; g.fill = GridBagConstraints.BOTH; p.add(catatan, g);
        return p;
    }

    private void tambahBaris(JPanel panel, int baris, String teks, JComponent komponen, int mnemonic) {
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(3, 6, 3, 6);
        g.gridy = baris;

        JLabel label = new JLabel(teks);
        if (mnemonic != 0) {
            label.setDisplayedMnemonic(mnemonic);
            label.setLabelFor(komponen);
        }
        g.gridx = 0; g.weightx = 0; g.anchor = GridBagConstraints.WEST;
        panel.add(label, g);

        g.gridx = 1; g.weightx = 1; g.fill = GridBagConstraints.HORIZONTAL;
        panel.add(komponen, g);
    }

    private JPanel buatPanelTombol() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p.add(btnKirim);
        return p;
    }

    private static class UrutanFokus extends FocusTraversalPolicy {
        private final List<Component> urutan = new ArrayList<>();

        UrutanFokus(List<? extends Component> daftar) { urutan.addAll(daftar); }

        private int idx(Component c) { return urutan.indexOf(c); }

        @Override public Component getComponentAfter(Container r, Component c) {
            return urutan.get((idx(c) + 1) % urutan.size());
        }
        @Override public Component getComponentBefore(Container r, Component c) {
            return urutan.get((idx(c) - 1 + urutan.size()) % urutan.size());
        }
        @Override public Component getFirstComponent(Container r) { return urutan.get(0); }
        @Override public Component getLastComponent(Container r) { return urutan.get(urutan.size() - 1); }
        @Override public Component getDefaultComponent(Container r) { return urutan.get(0); }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormPendaftaran().setVisible(true));
    }
}