import Vue from 'vue';
import Vuex from 'vuex';
import { apiGo, apiJava } from '../apis';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    patients: [],
    patient: {},
  },
  mutations: {
    SET_PATIENTS(state, payload) {
      state.patients = payload.patients;
    },
    SET_PATIENT(state, payload) {
      state.patient = payload.patient;
    },
  },
  actions: {
    async fetchPatients(context) {
      try {
        const { data: patients } = await apiGo({
          method: 'GET',
          url: `/patients`,
        });
        context.commit('SET_PATIENTS', { patients });
      } catch (error) {
        console.log(error.response.data.message);
      }
    },
    async fetchPatient(context, id) {
      try {
        const { data: patient } = await apiGo({
          method: 'GET',
          url: `/patients/${id}`,
        });
        context.commit('SET_PATIENT', { patient });
      } catch (error) {
        console.log(error.response.data.message);
      }
    },
    async addPatient(context, patient) {
      try {
        const { data: newPatient } = await apiJava({
          method: 'POST',
          url: `/patients`,
          data: patient,
        });
        console.log(newPatient, 'ADDED!');
        this.dispatch('fetchPatients');
      } catch (error) {
        console.log(error.response.data.message);
      }
    },
    async editPatient(context, id, patient) {
      try {
        const { data: edittedPatient } = await apiJava({
          method: 'PUT',
          url: `/patients/${id}`,
          data: patient,
        });
        console.log(edittedPatient, 'EDITTED');
        this.dispatch('fetchPatients');
      } catch (error) {
        console.log(error.response.data.message);
      }
    },
    async deletePatient(context, id) {
      try {
        const { data: deletedPatient } = await apiJava({
          method: 'DELETE',
          // url: `/patients/ids=${ids}`,
          url: `/patients/${id}`,
        });
        console.log(deletedPatient);
        this.dispatch('fetchPatients');
      } catch (error) {
        console.log(error.response.data.message);
      }
    },
  },
});
