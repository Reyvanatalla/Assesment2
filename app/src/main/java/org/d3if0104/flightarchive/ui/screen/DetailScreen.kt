package org.d3if0104.flightarchive.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.d3if0104.flightarchive.R
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.d3if0104.flightarchive.ui.theme.Mobpro1Theme
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0104.flightarchive.database.FlightDb
import org.d3if0104.flightarchive.util.ViewModelFactory


const val KEY_ID_FLIGHT = "idFlight"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null){
    val contenx = LocalContext.current
    val db = FlightDb.getInstance(contenx)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var namaMaskapai by remember { mutableStateOf("") }
    var nomorPenerbangan by remember { mutableStateOf("") }
    var tanggalPenerbangan by remember { mutableStateOf("") }
    var jamPenerbangan by remember { mutableStateOf("") }
    var kelas by remember { mutableStateOf("") }
    var jenisPenerbangan by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getFlight(id) ?: return@LaunchedEffect
        namaMaskapai = data.namaMaskapai
        nomorPenerbangan = data.nomorPenerbangan
        tanggalPenerbangan = data.tanggalPenerbangan
        jamPenerbangan= data.jamPenerbangan
        kelas = data.kelas
       jenisPenerbangan = data.jenisPenerbangan
    }

    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_flight))
                    else
                        Text(text = stringResource(id = R.string.edit_flight))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        if (namaMaskapai == "" || nomorPenerbangan == "" || tanggalPenerbangan == "" || jamPenerbangan == "" || kelas == "" || jenisPenerbangan == "") {
                            Toast.makeText(contenx, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null) {
                            viewModel.insert(namaMaskapai, nomorPenerbangan, tanggalPenerbangan, jamPenerbangan, kelas, jenisPenerbangan)
                        } else {
                            viewModel.update(id, namaMaskapai, nomorPenerbangan, tanggalPenerbangan, jamPenerbangan, kelas, jenisPenerbangan)
                        }
                        navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if ( id != null ) {
                        DeleteAction { showDialog = true}
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = { showDialog = false }) {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    ) { padding ->
        FormFlight(
            namaMaskapai = namaMaskapai,
            onNamaMaskapaiChange = { namaMaskapai = it },
            nomorPenerbangan = nomorPenerbangan,
            onNomorPenerbanganChange = { nomorPenerbangan = it },
            tanggalPenerbangan = tanggalPenerbangan,
            onTanggalPenerbanganChange = {tanggalPenerbangan = it },
            jamPenerbangan = jamPenerbangan,
            onJamPenerbanganChange = {jamPenerbangan = it },
            kelas = kelas,
            onKelasChange = {kelas = it },
            jenisPenerbangan =jenisPenerbangan,
            onJenisPenerbanganChange = {jenisPenerbangan = it },
            modifier = Modifier.padding(padding)
        )

    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var  expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.hapus))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Composable
fun FormFlight(
    namaMaskapai: String, onNamaMaskapaiChange: (String) -> Unit,
    nomorPenerbangan: String, onNomorPenerbanganChange: (String) -> Unit,
    tanggalPenerbangan: String, onTanggalPenerbanganChange: (String) -> Unit,
    jamPenerbangan: String, onJamPenerbanganChange: (String) -> Unit,
    kelas: String, onKelasChange: (String) -> Unit,
    jenisPenerbangan: String, onJenisPenerbanganChange: (String) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = namaMaskapai,
            onValueChange = { onNamaMaskapaiChange(it) },
            label = { Text(text = stringResource(R.string.nama)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = nomorPenerbangan,
            onValueChange = { onNomorPenerbanganChange(it) },
            label = { Text(text = stringResource(R.string.nomorPenerbangan)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
                value = tanggalPenerbangan,
        onValueChange = { onTanggalPenerbanganChange(it) },
        label = { Text(text = stringResource(R.string.tanggalPenerbangan)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = jamPenerbangan,
            onValueChange = { onJamPenerbanganChange(it) },
            label = { Text(text = stringResource(R.string.jamPenerbangan)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )


        OutlinedCard(modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp)
        ){
            Column {
                listOf(
                    "Economy class",
                    "Bussiness class",
                    "First class",

                ).forEach { classOption ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onKelasChange(classOption) }
                    ) {
                        RadioButton(
                            selected = classOption == kelas,
                            onClick = { onKelasChange(classOption) }
                        )
                        Text(text = classOption, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }
        OutlinedCard(modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp)
        ){
            Column {
                listOf(
                    "Perintis",
                    "Domestik",
                    "Internasional"

                ).forEach { classOption ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onJenisPenerbanganChange(classOption) }
                    ) {
                        RadioButton(
                            selected = classOption == jenisPenerbangan,
                            onClick = { onJenisPenerbanganChange(classOption) }
                        )
                        Text(text = classOption, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    Mobpro1Theme {
        DetailScreen(rememberNavController())
    }
}