package com.tuapp.clinicasalud.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.clinicasalud.data.Doctor
import com.tuapp.clinicasalud.data.MockData

val PrimaryPurple = Color(0xFF5B2C6F)
val LightPurple = Color(0xFFF3E5F5)

@Composable
fun AppDrawerContent(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onCloseDrawer: () -> Unit
) {
    ModalDrawerSheet {
        Box(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = LightPurple,
                    modifier = Modifier.size(50.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("JP", color = PrimaryPurple, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Juan Pérez", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("Paciente", color = Color.Gray, fontSize = 14.sp)
                }
            }
        }
        HorizontalDivider()
        Spacer(modifier = Modifier.height(12.dp))

        NavigationDrawerItem(
            label = { Text("Inicio") },
            selected = currentRoute == "home",
            onClick = { onNavigate("home"); onCloseDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text("Mis citas") },
            selected = currentRoute == "appointments",
            onClick = { onNavigate("appointments"); onCloseDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text("Historial médico") },
            selected = currentRoute == "history",
            onClick = { onNavigate("history"); onCloseDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text("Perfil") },
            selected = currentRoute == "profile",
            onClick = { onNavigate("profile"); onCloseDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onDoctorClick: (Int) -> Unit,
    onOpenDrawer: () -> Unit
) {
    val specialties = listOf("Todas", "Cardiología", "Pediatría", "Dermatología")
    var selectedSpecialty by remember { mutableStateOf("Todas") }

    val filteredDoctors = if (selectedSpecialty == "Todas") {
        MockData.sampleDoctors
    } else {
        MockData.sampleDoctors.filter { it.specialty == selectedSpecialty }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Clínica Salud+", fontWeight = FontWeight.Bold, color = Color.White)
                        Text("Hola, Juan", fontSize = 12.sp, color = Color.LightGray)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryPurple)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(specialties) { specialty ->
                    FilterChip(
                        selected = selectedSpecialty == specialty,
                        onClick = { selectedSpecialty = specialty },
                        label = { Text(specialty) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PrimaryPurple,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Médicos disponibles", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(filteredDoctors) { doctor ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onDoctorClick(doctor.id) },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = LightPurple,
                                modifier = Modifier.size(48.dp)
                            ) {
                                Icon(Icons.Default.Person, contentDescription = null, tint = PrimaryPurple, modifier = Modifier.padding(8.dp))
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(doctor.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text(doctor.specialty, color = Color.Gray, fontSize = 13.sp)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(18.dp))
                                Text(" ${doctor.rating}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorProfileScreen(
    doctorId: Int,
    onBackClick: () -> Unit,
    onScheduleClick: (Int) -> Unit
) {
    val doctor = MockData.sampleDoctors.find { it.id == doctorId } ?: MockData.sampleDoctors[0]

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil del médico") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = CircleShape,
                color = LightPurple,
                modifier = Modifier.size(100.dp)
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = PrimaryPurple, modifier = Modifier.padding(20.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(doctor.name, fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Text("${doctor.specialty} · ${doctor.experienceYears} años exp.", color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(20.dp))
                Text(" ${doctor.rating} (${doctor.reviewCount} reseñas)", fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(doctor.biography, color = Color.DarkGray, fontSize = 15.sp)

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { onScheduleClick(doctor.id) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple)
            ) {
                Text("Agendar cita", fontSize = 16.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAppointmentScreen(
    doctorId: Int,
    onBackClick: () -> Unit,
    onConfirmClick: (String, String, String) -> Unit
) {
    val doctor = MockData.sampleDoctors.find { it.id == doctorId } ?: MockData.sampleDoctors[0]
    var selectedDate by remember { mutableStateOf(doctor.availableDates.first()) }
    var selectedTime by remember { mutableStateOf(doctor.availableTimes.first()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agendar cita") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            Text("Selecciona fecha", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                doctor.availableDates.forEach { date ->
                    FilterChip(
                        selected = selectedDate == date,
                        onClick = { selectedDate = date },
                        label = { Text(date) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PrimaryPurple,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Selecciona hora", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                doctor.availableTimes.forEach { time ->
                    FilterChip(
                        selected = selectedTime == time,
                        onClick = { selectedTime = time },
                        label = { Text(time) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PrimaryPurple,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { onConfirmClick(doctor.name, selectedDate, selectedTime) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple)
            ) {
                Text("Confirmar cita", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun ConfirmationScreen(
    doctorName: String,
    date: String,
    time: String,
    onSeeAppointments: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            shape = CircleShape,
            color = Color(0xFFE8F5E9),
            modifier = Modifier.size(90.dp)
        ) {
            Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.padding(20.dp))
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text("¡Cita agendada!", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(doctorName, fontSize = 16.sp, color = Color.Gray)
        Text("$date, $time", fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = onSeeAppointments,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFECEFF1))
        ) {
            Text("Ver mis citas", color = Color.Black)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentsScreen(onOpenDrawer: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis citas") },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(MockData.sampleAppointments) { appt ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(appt.doctorName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(appt.dateTime, color = Color.Gray, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        SuggestionChip(
                            onClick = {},
                            label = { Text(appt.status) },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = if (appt.status == "Confirmada") Color(0xFFE8F5E9) else Color(0xFFEEEEEE),
                                labelColor = if (appt.status == "Confirmada") Color(0xFF2E7D32) else Color.Gray
                            )
                        )
                    }
                }
            }
        }
    }
}