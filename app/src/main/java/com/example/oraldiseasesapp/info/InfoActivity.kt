package com.example.oraldiseasesapp.info

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.oraldiseasesapp.MainActivity
import com.example.oraldiseasesapp.R
import com.example.oraldiseasesapp.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            normal.setOnClickListener {
                startDetailActivity("Normal", "Gigi normal dan sehat adalah gigi yang bebas dari kerusakan, penyakit, dan masalah struktural. Gigi sehat memiliki warna putih atau krem alami, tidak terasa nyeri saat digunakan untuk mengunyah, dan memiliki struktur kuat yang mendukung fungsi makan, berbicara, dan estetika.\n" +
                        "\n" +
                        "Ciri-ciri Gigi Normal dan Sehat\n" +
                        "1. Warna: Gigi sehat memiliki warna putih atau krem alami tanpa noda yang mencolok.\n" +
                        "2. Gusi: Gusi yang sehat berwarna merah muda, tidak bengkak, dan tidak mudah berdarah.\n" +
                        "3. Struktur: Tidak ada gigi berlubang, patah, atau goyah.\n" +
                        "4. Bau Mulut: Mulut yang sehat bebas dari bau mulut yang tidak sedap (halitosis).\n" +
                        "5. Kebersihan: Permukaan gigi terasa halus dan tidak ada plak atau karang gigi yang menumpuk.\n" +
                        "Cara Merawat Gigi Agar Tetap Normal dan Sehat\n" +
                        "1. Sikat Gigi Secara Teratur\n" +
                        "\n" +
                        "- Sikat gigi dua kali sehari menggunakan pasta gigi yang mengandung fluoride.\n" +
                        "- Gunakan sikat gigi dengan bulu lembut untuk menghindari iritasi gusi.\n" +
                        "- Gunakan Benang Gigi\n" +
                        "\n" +
                        "Bersihkan sela-sela gigi dengan dental floss setiap hari untuk mencegah penumpukan plak.\n" +
                        "Batasi Konsumsi Gula\n" +
                        "\n" +
                        "Kurangi makanan dan minuman manis seperti permen, soda, atau kue yang dapat menyebabkan kerusakan gigi.\n" +
                        "Konsumsi Makanan Bergizi\n" +
                        "\n" +
                        "Perbanyak makan sayuran, buah-buahan, dan makanan tinggi kalsium seperti susu dan keju.\n" +
                        "Rutin Periksa ke Dokter Gigi\n" +
                        "\n" +
                        "Lakukan pemeriksaan gigi setidaknya setiap 6 bulan sekali untuk memastikan kesehatan gigi tetap terjaga.\n" +
                        "Manfaat Memiliki Gigi Normal dan Sehat\n" +
                        "Kesehatan Mulut yang Optimal: Mengurangi risiko penyakit gusi, gigi berlubang, dan infeksi lainnya.\n" +
                        "Fungsi yang Baik: Membantu proses pengunyahan dan berbicara secara normal.\n" +
                        "Kepercayaan Diri: Senyum yang sehat meningkatkan rasa percaya diri.\n" +
                        "Kesehatan Tubuh Secara Keseluruhan: Gigi yang sehat dapat mencegah masalah kesehatan lain seperti penyakit jantung atau diabetes.\n" +
                        "Fakta Menarik\n" +
                        "Orang dewasa memiliki 32 gigi permanen, termasuk gigi geraham bungsu.\n" +
                        "Enamel gigi adalah bagian tubuh manusia yang paling keras, bahkan lebih keras dari tulang.\n" +
                        "Tips Khusus\n" +
                        "Gunakan mouthwash antibakteri untuk menjaga kebersihan mulut lebih optimal.\n" +
                        "Hindari menggigit benda keras seperti es atau pena, yang dapat merusak gigi.", R.drawable.normal,  "Chlorhexidine", "Beli di toko online terdekat", "https://www.tokopedia.com/serafikamedika/perio-kin-spray-chlorhexidine-digluconate-0-20?extParam=ivf%3Dfalse&src=topads")
            }
            caries.setOnClickListener {
                startDetailActivity("Caries", "Calculus, atau tartar, adalah penumpukan plak gigi yang mengeras karena mineralisasi. Plak yang tidak dibersihkan secara teratur dapat berubah menjadi calculus, yang biasanya berwarna kuning atau cokelat dan menempel erat pada gigi atau garis gusi. Jika dibiarkan, calculus dapat menyebabkan masalah seperti gingivitis (radang gusi) atau periodontitis (penyakit gusi yang lebih parah).\n" +
                        "\n" +
                        "Cara Penanganan\n" +
                        "\n" +
                        "1. Scalling gigi dengan prosedur pembersihan gigi profesional oleh dokter gigi untuk menghilangkan calculus menggunakan alat ultrasonik atau manual.\n" +
                        "2. Sikat gigi menggunakan pasta gigi berfluorida dua kali sehari untuk mencegah pembentukan plak baru.\n" +
                        "3. Kumur dengan obat antiseptik dengan menggunakan obat kumur antiseptik untuk membantu mengurangi bakteri di mulut.\n" +
                        "4. Lakukan pemeriksaan gigi secara rutin setidaknya setiap 6 bulan sekali untuk mencegah kalkulus berkembang lebih lanjut.\n" +
                        "\n Obat yang Disarankan\n" +
                        "\n" +
                        "1. Pasta gigi khusus yang mengandung pyrophosphate atau zinc citrate untuk membantu mencegah penumpukan kalkulus.\n" +
                        "2. Obat kumur antiseptik seperti chlorhexidine untuk mengontrol bakteri penyebab plak.\n" +
                        "3. Salep atau gel topikal untuk mengurangi peradangan pada gusi (jika ada gingivitis).", R.drawable.caries,  "Chlorhexidine", "Beli di toko online terdekat", "https://www.tokopedia.com/serafikamedika/perio-kin-spray-chlorhexidine-digluconate-0-20?extParam=ivf%3Dfalse&src=topads")
            }
            gingivitis.setOnClickListener {
                startDetailActivity("Gingivitis", "Gingivitis adalah bentuk ringan dari penyakit periodontal yang menyebabkan iritasi, kemerahan, dan pembengkakan pada gusi, terutama di sekitar pangkal gigi. Jika tidak diobati, gingivitis dapat berkembang menjadi periodontitis, yang lebih serius dan dapat menyebabkan kerusakan jaringan dan tulang yang menyokong gigi.\n" +
                        "\n" +
                        "Penyebab\n" +
                        "\n" +
                        "Penumpukan plak, lapisan lengket yang terdiri dari bakteri di gigi.\n" +
                        "Kebersihan mulut yang buruk.\n" +
                        "Perubahan hormonal (misalnya, selama kehamilan atau menopause).\n" +
                        "Kondisi medis seperti diabetes atau infeksi tertentu.\n" +
                        "Merokok atau penggunaan tembakau.\n" +
                        "Penggunaan obat-obatan tertentu yang mengurangi produksi air liur.\n" +
                        "Gejala\n" +
                        "\n" +
                        "Gusi bengkak, merah, dan mudah berdarah, terutama saat menyikat gigi atau menggunakan benang gigi.\n" +
                        "Bau mulut yang persisten.\n" +
                        "Penarikan gusi dari gigi (resesi gusi).\n" +
                        "Rasa tidak nyaman di sekitar gusi.\n" +
                        "Cara Penanganan\n" +
                        "\n" +
                        "Perawatan di Rumah:\n" +
                        "Sikat gigi setidaknya dua kali sehari dengan pasta gigi yang mengandung fluoride.\n" +
                        "Gunakan benang gigi setiap hari untuk membersihkan sela-sela gigi.\n" +
                        "Berkumur dengan obat kumur antiseptik.\n" +
                        "Perawatan Profesional:\n" +
                        "Pembersihan gigi profesional oleh dokter gigi (scaling) untuk menghilangkan plak dan tartar.\n" +
                        "Jika gingivitis parah, dokter gigi mungkin meresepkan antibiotik atau prosedur lanjutan.\n" +
                        "Obat yang Disarankan\n" +
                        "\n" +
                        "Obat kumur antiseptik yang mengandung klorheksidin atau hidrogen peroksida.\n" +
                        "Gel antibiotik yang diterapkan langsung pada gusi.\n" +
                        "Obat anti-inflamasi seperti ibuprofen untuk meredakan pembengkakan dan nyeri (jika diperlukan).\n" +
                        "Tips Pencegahan\n" +
                        "\n" +
                        "Jaga kebersihan mulut dengan menyikat dan menggunakan benang gigi secara teratur.\n" +
                        "Kurangi konsumsi makanan manis yang dapat memicu pertumbuhan bakteri.\n" +
                        "Periksa kesehatan gigi secara rutin setidaknya dua kali setahun.\n" +
                        "Hindari merokok atau penggunaan produk tembakau.\n" +
                        "Konsumsi makanan kaya vitamin C dan kalsium untuk mendukung kesehatan gusi.", R.drawable.gingivitis, "Chlorhexidine", "Beli di toko online terdekat", "https://www.tokopedia.com/serafikamedika/perio-kin-spray-chlorhexidine-digluconate-0-20?extParam=ivf%3Dfalse&src=topads")
            }
            hypodontia.setOnClickListener {
                startDetailActivity("Hypodontia", "Hypodontia adalah kondisi bawaan yang ditandai dengan kurangnya satu atau lebih gigi permanen. Ini adalah bentuk ringan dari gangguan perkembangan gigi, yang dapat memengaruhi estetika senyum, fungsi pengunyahan, dan kesehatan mulut secara keseluruhan. Hypodontia paling sering memengaruhi gigi seri lateral atas, premolar kedua, atau gigi molar ketiga (gigi bungsu).\n" +
                        "\n" +
                        "Penyebab\n" +
                        "\n" +
                        "Faktor Genetik: Hypodontia sering diwariskan dan terkait dengan sindrom tertentu seperti sindrom Down atau ectodermal dysplasia.\n" +
                        "Gangguan Perkembangan: Masalah selama perkembangan gigi pada masa kanak-kanak.\n" +
                        "Faktor Lingkungan: Cedera, infeksi, atau terapi radiasi selama perkembangan gigi.\n" +
                        "Gejala\n" +
                        "\n" +
                        "Hilangnya satu atau lebih gigi permanen.\n" +
                        "Perubahan pada struktur gigi dan rahang (seperti ruang kosong di gusi).\n" +
                        "Maloklusi atau masalah gigitan akibat gigi yang tidak sejajar.\n" +
                        "Gigi yang tersisa mungkin lebih kecil dari ukuran normal (microdontia).\n" +
                        "Cara Penanganan\n" +
                        "\n" +
                        "Evaluasi oleh Dokter Gigi: Diagnosis melalui pemeriksaan klinis dan sinar-X untuk menentukan jumlah dan posisi gigi yang hilang.\n" +
                        "Pilihan Perawatan:\n" +
                        "Braces (Kawat Gigi): Untuk merapikan gigi yang ada dan mengoptimalkan estetika.\n" +
                        "Implan Gigi: Penggantian gigi yang hilang dengan implan yang permanen.\n" +
                        "Bridge Gigi: Gigi palsu yang terpasang pada gigi yang ada di sekitarnya.\n" +
                        "Dentures (Gigi Tiruan): Untuk kasus yang melibatkan beberapa gigi yang hilang.\n" +
                        "Crown atau Veneer: Untuk memperbaiki penampilan gigi yang ada jika diperlukan.\n" +
                        "Obat yang Disarankan\n" +
                        "Hypodontia tidak memerlukan obat secara langsung, namun pengelolaan komplikasi seperti rasa sakit atau infeksi akibat perubahan struktur gigi dapat menggunakan:\n" +
                        "\n" +
                        "Analgesik (Parasetamol atau Ibuprofen): Untuk mengatasi nyeri.\n" +
                        "Antibiotik: Jika ada infeksi pada jaringan gusi.\n" +
                        "Tips Pencegahan\n" +
                        "Hypodontia adalah kondisi bawaan yang tidak dapat dicegah sepenuhnya, namun langkah-langkah berikut dapat membantu:\n" +
                        "\n" +
                        "Periksa secara rutin: Pemeriksaan gigi pada usia dini untuk mendeteksi masalah perkembangan gigi.\n" +
                        "Konsultasi genetik: Jika ada riwayat hypodontia dalam keluarga, pertimbangkan konsultasi genetik.\n" +
                        "Perawatan orthodontic dini: Untuk memastikan perkembangan gigi yang optimal dan mengelola ruang untuk gigi yang hilang.\n" +
                        "Dampak Psikologis\n" +
                        "Kehilangan gigi dapat memengaruhi kepercayaan diri, terutama pada anak-anak. Oleh karena itu, penting untuk mendukung pasien secara emosional dan memberikan solusi estetika yang sesuai.", R.drawable.hypodontia,  "Chlorhexidine", "Beli di toko online terdekat", "https://www.tokopedia.com/serafikamedika/perio-kin-spray-chlorhexidine-digluconate-0-20?extParam=ivf%3Dfalse&src=topads")
            }
            calculus.setOnClickListener {
                startDetailActivity("Calculus", "Calculus, atau karang gigi, adalah plak gigi yang telah mengeras karena mineralisasi, biasanya di sekitar garis gusi atau pada permukaan gigi. Calculus sulit dihilangkan dengan menyikat biasa dan membutuhkan perawatan profesional oleh dokter gigi. Jika dibiarkan, calculus dapat menyebabkan berbagai masalah kesehatan mulut, seperti gingivitis dan penyakit periodontal.\n" +
                        "\n" +
                        "Penyebab\n" +
                        "\n" +
                        "Penumpukan Plak: Plak yang tidak dibersihkan akan mengeras menjadi calculus.\n" +
                        "Kebersihan Mulut yang Buruk: Jarang menyikat gigi atau tidak membersihkan sela-sela gigi dengan flossing.\n" +
                        "Diet Tinggi Gula atau Karbohidrat: Mempercepat pembentukan plak.\n" +
                        "Produksi Air Liur yang Tinggi: Air liur yang kaya mineral dapat mempercepat mineralisasi plak menjadi calculus.\n" +
                        "Merokok: Meningkatkan risiko pembentukan karang gigi.\n" +
                        "Gejala\n" +
                        "\n" +
                        "Lapisan keras berwarna kuning atau cokelat pada permukaan gigi.\n" +
                        "Gusi meradang, merah, dan mudah berdarah saat menyikat atau flossing.\n" +
                        "Napas yang tidak sedap (halitosis).\n" +
                        "Gigi terasa kasar saat disentuh.\n" +
                        "Penurunan gusi di sekitar daerah dengan calculus.\n" +
                        "Cara Penanganan\n" +
                        "\n" +
                        "Scaling oleh Dokter Gigi: Prosedur pembersihan profesional untuk menghilangkan calculus dari gigi.\n" +
                        "Root Planing: Untuk calculus yang berada di bawah garis gusi, membantu mencegah infeksi lebih lanjut.\n" +
                        "Perawatan Periodontal Lanjutan: Jika calculus menyebabkan penyakit periodontal.\n" +
                        "Obat yang Disarankan\n" +
                        "Obat digunakan untuk mengatasi komplikasi akibat calculus, seperti infeksi atau peradangan:\n" +
                        "\n" +
                        "Antiseptik Mulut (Chlorhexidine): Untuk mengurangi bakteri di rongga mulut.\n" +
                        "Antibiotik Topikal: Jika terjadi infeksi pada gusi akibat calculus.\n" +
                        "Obat Anti-inflamasi (Ibuprofen): Untuk mengurangi nyeri atau pembengkakan pada gusi.\n" +
                        "Tips Pencegahan\n" +
                        "\n" +
                        "Menyikat Gigi Secara Teratur: Dua kali sehari dengan pasta gigi yang mengandung fluoride.\n" +
                        "Flossing: Setiap hari untuk membersihkan sela-sela gigi.\n" +
                        "Gunakan Obat Kumur Antiseptik: Untuk membantu mengurangi pembentukan plak.\n" +
                        "Diet Seimbang: Hindari makanan yang tinggi gula atau tepung.\n" +
                        "Kunjungi Dokter Gigi Secara Rutin: Setiap 6 bulan untuk pembersihan profesional dan pemeriksaan gigi.\n" +
                        "Dampak Jika Tidak Ditangani\n" +
                        "\n" +
                        "Gingivitis (radang gusi).\n" +
                        "Penyakit periodontal yang lebih serius, seperti periodontitis.\n" +
                        "Kehilangan gigi akibat kerusakan jaringan pendukung gigi.\n" +
                        "Penurunan estetika gigi, seperti noda kuning atau cokelat yang terlihat jelas.", R.drawable.calculus,  "Chlorhexidine", "Beli di toko online terdekat", "https://www.tokopedia.com/serafikamedika/perio-kin-spray-chlorhexidine-digluconate-0-20?extParam=ivf%3Dfalse&src=topads")
            }
            ulcer.setOnClickListener {
                startDetailActivity("Ulcer", "Ulcer, atau lebih dikenal sebagai sariawan, adalah luka kecil yang berkembang di dalam mulut, biasanya di lidah, pipi bagian dalam, gusi, atau langit-langit mulut. Luka ini bisa terasa sangat menyakitkan, terutama saat makan, minum, atau berbicara. Meskipun umumnya tidak berbahaya, ulkus mulut dapat menyebabkan ketidaknyamanan yang signifikan.\n" +
                        "\n" +
                        "Penyebab\n" +
                        "\n" +
                        "Cedera ringan pada mulut (misalnya, tergigit atau tergores oleh makanan keras).\n" +
                        "Kekurangan nutrisi tertentu seperti vitamin B12, zat besi, atau asam folat.\n" +
                        "Infeksi virus atau bakteri.\n" +
                        "Reaksi alergi terhadap makanan tertentu.\n" +
                        "Stres atau gangguan hormonal.\n" +
                        "Kondisi medis tertentu seperti penyakit Crohn atau celiac.\n" +
                        "Cara Penanganan\n" +
                        "\n" +
                        "Pengobatan di Rumah:\n" +
                        "Berkumur dengan larutan air garam hangat.\n" +
                        "Hindari makanan pedas, asam, atau kasar yang dapat memperparah rasa sakit.\n" +
                        "Gunakan sedotan untuk minuman.\n" +
                        "Obat Topikal:\n" +
                        "Salep atau gel khusus yang mengandung lidokain untuk meredakan rasa sakit.\n" +
                        "Perawatan Dokter:\n" +
                        "Jika ulkus berlangsung lebih dari dua minggu, konsultasikan ke dokter atau dokter gigi untuk pemeriksaan lebih lanjut.\n" +
                        "Obat yang Disarankan\n" +
                        "\n" +
                        "Topikal:\n" +
                        "Gel lidokain atau benzokain untuk mengurangi rasa sakit.\n" +
                        "Salep kortikosteroid seperti triamcinolone untuk mengurangi peradangan.\n" +
                        "Antiseptik Mulut:\n" +
                        "Cairan kumur antiseptik untuk mencegah infeksi sekunder.\n" +
                        "Suplemen:\n" +
                        "Vitamin B12, zat besi, atau asam folat jika ulkus terkait dengan kekurangan nutrisi.\n" +
                        "Tips Pencegahan\n" +
                        "\n" +
                        "Jaga kebersihan mulut dengan menyikat gigi secara teratur menggunakan sikat berbulu lembut.\n" +
                        "Hindari makanan yang memicu ulkus seperti makanan asam atau pedas.\n" +
                        "Kelola stres dengan baik melalui olahraga, meditasi, atau aktivitas relaksasi.\n" +
                        "Periksa kesehatan gigi secara rutin ke dokter gigi.", R.drawable.ulcer, "Chlorhexidine", "Beli di toko online terdekat", "https://www.tokopedia.com/serafikamedika/perio-kin-spray-chlorhexidine-digluconate-0-20?extParam=ivf%3Dfalse&src=topads")
            }
            discoloration.setOnClickListener {
                startDetailActivity("Discoloration", "Tooth discoloration adalah perubahan warna pada gigi yang membuatnya tampak lebih kuning, cokelat, abu-abu, atau bahkan hitam. Kondisi ini dapat terjadi pada lapisan luar (email gigi) atau bagian dalam gigi (dentin). Tooth discoloration biasanya disebabkan oleh faktor eksternal seperti makanan, minuman, atau kebiasaan buruk, tetapi juga bisa karena kondisi internal seperti trauma gigi atau penyakit.\n" +
                        "\n" +
                        "Penyebab\n" +
                        "\n" +
                        "Eksternal:\n" +
                        "Noda dari makanan/minuman seperti kopi, teh, anggur merah, soda, atau tembakau (merokok dan mengunyah).\n" +
                        "Internal:\n" +
                        "Trauma pada gigi, penggunaan antibiotik seperti tetracycline saat masa pertumbuhan gigi, atau paparan fluoride berlebih.\n" +
                        "Usia:\n" +
                        "Penipisan enamel secara alami dengan bertambahnya usia, memperlihatkan dentin yang lebih kuning.\n" +
                        "Kondisi Medis:\n" +
                        "Penyakit tertentu atau perawatan seperti kemoterapi dapat menyebabkan perubahan warna.\n" +
                        "Cara Penanganan\n" +
                        "\n" +
                        "Pemutihan Gigi Profesional:\n" +
                        "Dilakukan oleh dokter gigi menggunakan gel pemutih berbasis hidrogen peroksida atau karbamid peroksida.\n" +
                        "Pasta Gigi Pemutih:\n" +
                        "Gunakan pasta gigi yang dirancang untuk menghilangkan noda eksternal.\n" +
                        "Perawatan Veneer atau Bonding:\n" +
                        "Untuk noda yang parah, dokter gigi dapat menutupi permukaan gigi dengan bahan veneer atau bonding.\n" +
                        "Pencegahan:\n" +
                        "Hindari makanan/minuman penyebab noda, berhenti merokok, dan sikat gigi dua kali sehari.\n" +
                        "Obat yang Disarankan\n" +
                        "\n" +
                        "Produk Pemutih Over-the-Counter:\n" +
                        "Strips pemutih, gel pemutih, atau tray kit yang dijual bebas.\n" +
                        "Produk dengan Fluoride:\n" +
                        "Membantu memperkuat enamel gigi untuk mencegah penipisan lebih lanjut.\n" +
                        "Pasta Gigi Khusus:\n" +
                        "Yang mengandung bahan abrasif ringan untuk membersihkan noda.\n" +
                        "Tips Tambahan\n" +
                        "\n" +
                        "Gunakan sedotan untuk meminum kopi atau teh guna mengurangi kontak cairan dengan gigi.\n" +
                        "Rutin berkonsultasi dengan dokter gigi untuk memonitor kondisi gigi.", R.drawable.discoloration, "Chlorhexidine", "Beli di toko online terdekat", "https://www.tokopedia.com/serafikamedika/perio-kin-spray-chlorhexidine-digluconate-0-20?extParam=ivf%3Dfalse&src=topads")
            }
        }

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun startDetailActivity(title: String, desc: String, imageRes: Int, titleObat: String, descObat: String, url_obat: String) {
        val intent = Intent(this, DetailInfoActivity::class.java).apply {
            putExtra("TITLE", title)
            putExtra("DESC", desc)
            putExtra("IMAGE_RES", imageRes)
            putExtra("TITLE_OBAT", titleObat)
            putExtra("DESC_OBAT", descObat)
            putExtra("URL_OBAT", url_obat)
        }
        startActivity(intent)
    }
}