package com.example.randomchoiceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.randomchoiceapp.ui.theme.FilmeDaVezTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FilmeDaVezTheme {
                FilmeDaVezApp()
            }
        }
    }
}

data class Filme(
    val titulo: String,
    val genero: String,
    val descricao: String,
    val emoji: String
)

val listaFilmes = listOf(
    Filme("De Volta para o Futuro", "🎭 Aventura / Ficção Científica",
        "Marty McFly viaja no tempo usando um DeLorean modificado pelo excêntrico Doc Brown. Uma aventura inesquecível pelos anos 1950!", "🚗⚡"),
    Filme("Parasita", "🎭 Suspense / Drama",
        "Uma família pobre se infiltra na vida de uma família rica de forma inesperada. Vencedor do Oscar de Melhor Filme!", "🏠🪲"),
    Filme("Interestelar", "🚀 Ficção Científica / Drama",
        "Um grupo de astronautas viaja pelo universo em busca de um novo lar para a humanidade. Prepare os lenços!", "🌌❤️"),
    Filme("O Diabo Veste Prada", "👗 Comédia / Drama",
        "Uma jovem jornalista vai trabalhar para a editora de moda mais temida de Nova York. Estilo e drama garantidos!", "👠💄"),
    Filme("Coringa", "🃏 Drama / Suspense",
        "A origem perturbadora do vilão mais icônico do Batman. Joaquin Phoenix em uma atuação arrepiante!", "🎭🃏"),
    Filme("Mamma Mia!", "🎵 Musical / Comédia",
        "Uma noiva quer descobrir quem é seu pai antes do casamento. ABBA do início ao fim!", "🎤💃"),
    Filme("A Origem", "🌀 Ficção Científica / Ação",
        "Um ladrão especialista em roubar segredos dos sonhos recebe a missão de plantar uma ideia. Sua mente vai explodir!", "🌀🎯"),
    Filme("Vingadores: Ultimato", "🦸 Ação / Ficção Científica",
        "Os heróis mais poderosos do universo se unem para reverter as ações de Thanos. A batalha final épica!", "⚡🛡️"),
    Filme("La La Land", "🎵 Musical / Romance",
        "Um pianista e uma atriz se apaixonam em Los Angeles enquanto perseguem seus sonhos. Lindo e melancólico!", "⭐🎹"),
    Filme("Jurassic Park", "🦕 Aventura / Ficção Científica",
        "Um parque temático com dinossauros reais sai completamente do controle. Clássico absoluto do cinema!", "🦖🌿"),
    Filme("Divertida Mente 2", "😄 Animação / Aventura",
        "Riley cresce e novas emoções chegam para bagunçar tudo. Ansiedade, vergonha e muito mais! Para toda a família!", "🧠🫧"),
    Filme("O Senhor dos Anéis", "⚔️ Fantasia / Aventura",
        "Um hobbit recebe a missão de destruir o Um Anel e salvar a Terra-Média. Épico em todos os sentidos!", "💍🧙"),
    Filme("Titanic", "🚢 Romance / Drama",
        "Jack e Rose se apaixonam no navio mais famoso da história. Uma história de amor e tragédia inesquecível!", "🚢❤️"),
    Filme("Clube da Luta", "🥊 Drama / Suspense",
        "Um homem insone forma um clube clandestino de luta com um vendedor de sabão carismático. Reviravoltas épicas!", "🥊💥"),
    Filme("Comer, Rezar, Amar", "🌍 Drama / Romance",
        "Uma mulher larga tudo para viajar pela Itália, Índia e Bali em busca de si mesma. Inspirador demais!", "🍝🙏")
)

// Cores do tema
val BackgroundDark = Color(0xFF0D0D0D)
val CardBackground = Color(0xFF1A1A2E)
val Gold = Color(0xFFFFD700)
val GoldTransparent = Color(0x1AFFD700)
val GoldBorder = Color(0x80FFD700)
val TextPrimary = Color(0xFFFFFFFF)
val TextSecondary = Color(0xFFA0A0B0)
val DividerColor = Color(0xFF2A2A3E)

@Composable
fun FilmeDaVezApp() {
    var filmeAtual by remember { mutableStateOf<Filme?>(null) }
    var ultimoIndex by remember { mutableIntStateOf(-1) }
    var visivel by remember { mutableStateOf(false) }
    var btnPressed by remember { mutableStateOf(false) }

    // Animação de escala do botão
    val btnScale by animateFloatAsState(
        targetValue = if (btnPressed) 0.93f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "btnScale"
    )

    fun escolherFilme() {
        var novoIndex: Int
        do {
            novoIndex = Random.nextInt(listaFilmes.size)
        } while (novoIndex == ultimoIndex && listaFilmes.size > 1)
        ultimoIndex = novoIndex
        visivel = false
        filmeAtual = listaFilmes[novoIndex]
        visivel = true
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundDark
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .statusBarsPadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Header
            Text(text = "🎬", fontSize = 56.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Filme da Vez",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Gold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Deixa a gente escolher por você!",
                fontSize = 14.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Divider dourado decorativo
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(2.dp)
                    .background(Gold, RoundedCornerShape(1.dp))
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Card do filme com animação
            AnimatedVisibility(
                visible = visivel,
                enter = fadeIn(animationSpec = tween(400)) +
                        slideInVertically(
                            animationSpec = tween(400, easing = EaseOutCubic),
                            initialOffsetY = { it / 4 }
                        ),
                exit = fadeOut(animationSpec = tween(200))
            ) {
                filmeAtual?.let { filme ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = CardBackground),
                        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = filme.emoji, fontSize = 52.sp)
                            Spacer(modifier = Modifier.height(12.dp))

                            // Badge
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(GoldTransparent)
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(GoldTransparent),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "▶  ASSISTIR HOJE",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Gold,
                                        letterSpacing = 1.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = filme.titulo,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = filme.genero,
                                fontSize = 13.sp,
                                color = Gold,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            // Divider
                            HorizontalDivider(color = DividerColor)

                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = filme.descricao,
                                fontSize = 14.sp,
                                color = TextSecondary,
                                textAlign = TextAlign.Center,
                                lineHeight = 22.sp
                            )
                        }
                    }
                }
            }

            // Placeholder quando nenhum filme foi escolhido
            if (filmeAtual == null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🎥", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Toque no botão para\ndescobrir o filme da vez!",
                            fontSize = 15.sp,
                            color = TextSecondary,
                            textAlign = TextAlign.Center,
                            lineHeight = 22.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Botão principal
            Button(
                onClick = {
                    btnPressed = true
                    escolherFilme()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .scale(btnScale),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Gold),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                LaunchedEffect(btnPressed) {
                    if (btnPressed) {
                        kotlinx.coroutines.delay(150)
                        btnPressed = false
                    }
                }
                Text(
                    text = "🎲  Escolher Filme!",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = BackgroundDark
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "🍿 Pipoca na mão e bom filme!",
                fontSize = 13.sp,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}