<template>
  <div class="home">
    <div class=" py-4">
      <button
        v-on:click="showAddForm()"
        class="btn btn-md btn-primary col-md-2"
      >
        Add
      </button>
      <button
        v-on:click="goBulkDelete()"
        class="btn btn-md btn-danger col-md-2"
      >
        Delete
      </button>
    </div>
    <div v-if="isFormShown == true" class="patientForm">
      <form
        v-on:submit.prevent="submitForm()"
        class="col justify-content-center"
      >
        <div class="row justify-content-center">
          <div class="col-md-6">
            <label class="form-label">Name</label>
            <input v-model="name" type="text" class="form-control" />
          </div>
        </div>
        <div class="row justify-content-center">
          <div class=" col-md-6">
            <label class="form-label">Birth Date</label>
            <input v-model="birth_date" type="text" class="form-control" />
          </div>
        </div>
        <div class="row justify-content-center">
          <div class="col-md-6">
            <label class="form-label">Phone Number</label>
            <input v-model="phone_number" type="text" class="form-control" />
          </div>
        </div>
        <br />
        <div class="row justify-content-center">
          <div class="col-md-6">
            <button
              v-on:click="hideForm()"
              type="button"
              class="btn btn-md btn-dark col-md-4"
            >
              Cancel
            </button>
            <button
              v-if="isAddForm == true"
              type="submit"
              class="btn btn-md btn-primary col-md-4"
            >
              Add
            </button>
            <button
              v-else
              type="submit"
              class="btn btn-md btn-warning col-md-4"
            >
              Update
            </button>
          </div>
        </div>
      </form>
    </div>
    <div class="patientTable py-4">
      <table class="table table-hover">
        <thead>
          <tr>
            <th scope="col">ID</th>
            <th scope="col">Name</th>
            <th scope="col">Birth Date</th>
            <th scope="col">Phone Number</th>
            <th scope="col"></th>
            <th v-if="isCheckShown == true" scope="col">
              <button
                v-on:click="submitBulkDelete()"
                class="btn btn-sm btn-danger col-md-3"
              >
                Delete All
              </button>
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(patient, index) in patients" v-bind:key="index">
            <td>{{ patient.id }}</td>
            <td>{{ patient.name }}</td>
            <td>{{ patient.birth_date }}</td>
            <td>{{ patient.phone_number }}</td>
            <td>
              <button
                v-on:click="showEditForm(patient.id)"
                class="btn btn-sm btn-warning col-md-3"
              >
                Update
              </button>
              or
              <button
                v-on:click="deletePatient(patient.id)"
                class="btn btn-sm btn-danger col-md-3"
              >
                Delete
              </button>
            </td>
            <th v-if="isCheckShown == true" scope="col">
              <input
                class="form-check-input"
                type="checkbox"
                v-bind:value="patient.id"
                v-model="checkedIds"
              />
            </th>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
  name: 'Home',
  data() {
    return {
      isFormShown: false,
      isAddForm: false,

      isCheckShown: false,
      checkedIds: [],

      id: 0,
      name: '',
      birth_date: '',
      phone_number: '',
    };
  },
  created() {
    this.$store.dispatch('fetchPatients');
  },
  computed: {
    ...mapState(['patients']),
    patient() {
      return this.$store.state.patient;
    },
  },
  methods: {
    showAddForm() {
      this.name = '';
      this.birth_date = '';
      this.phone_number = '';

      this.isFormShown = true;
      this.isAddForm = true;
    },
    showEditForm(id) {
      this.$store.dispatch('fetchPatient', id);

      this.id = this.patient.id;
      this.name = this.patient.name;
      this.birth_date = this.patient.birth_date;
      this.phone_number = this.patient.phone_number;

      this.isFormShown = true;
      this.isAddForm = false;
    },
    hideForm() {
      this.isFormShown = false;
    },
    submitForm() {
      const id = this.id;
      const patient = {
        name: this.name,
        birthDate: this.birth_date,
        phoneNumber: this.phone_number,
      };
      console.log(patient, 'HOLAA');

      if (this.isAddForm) this.$store.dispatch('addPatient', patient);
      else this.$store.dispatch('editPatient', id, patient);

      this.isFormShown = false;
    },
    deletePatient(id) {
      this.$store.dispatch('deletePatient', id);
    },
    goBulkDelete() {
      this.isCheckShown = this.isCheckShown ? false : true;
    },
    submitBulkDelete() {
      const ids = this.checkedIds.join(',');
      this.$store.dispatch('deletePatient', ids);
      this.checkedIds = [];
    },
  },
};
</script>

<style>
.patientForm {
  padding-top: 2vh;
  padding-bottom: 2vh;
  background-color: whitesmoke;
}
</style>
